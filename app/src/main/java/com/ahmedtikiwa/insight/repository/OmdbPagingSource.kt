package com.ahmedtikiwa.insight.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ahmedtikiwa.insight.domain.SearchType
import com.ahmedtikiwa.insight.domain.SearchItem
import com.ahmedtikiwa.insight.network.OmdbService
import com.ahmedtikiwa.insight.network.models.asDomainModel
import retrofit2.HttpException
import java.io.IOException

class OmdbPagingSource(
    private val omdbApi: OmdbService,
    private val query: String,
    private val searchType: SearchType?
) : PagingSource<Int, SearchItem>() {

    override fun getRefreshKey(state: PagingState<Int, SearchItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> {
        val currentPosition = params.key ?: OMDB_START_INDEX

        val items: List<SearchItem>?

        return try {
            items = if (searchType == SearchType.MOVIE) {
                val searchResponse = omdbApi.getMovieSearchAsync(
                    s = query,
                    page = currentPosition
                ).await()
                searchResponse.Search.asDomainModel()
            } else {
                val searchResponse =
                    omdbApi.getSeriesSearchAsync(
                        s = query,
                        page = currentPosition
                    ).await()
                searchResponse.Search.asDomainModel()
            }

            LoadResult.Page(
                data = items ?: emptyList(),
                prevKey = if (currentPosition == OMDB_START_INDEX) null else currentPosition - 1,
                nextKey = if (items.isNullOrEmpty()) null else currentPosition + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val OMDB_START_INDEX = 1
    }
}