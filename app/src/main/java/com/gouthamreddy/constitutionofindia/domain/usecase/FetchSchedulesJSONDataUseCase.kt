package com.gouthamreddy.constitutionofindia.domain.usecase

import com.gouthamreddy.constitutionofindia.data.models.SchedulesResponse
import com.gouthamreddy.constitutionofindia.domain.AppRepository
import com.gouthamreddy.constitutionofindia.domain.UseCase
import com.gouthamreddy.constitutionofindia.domain.toResult

/**
 * We are fetching entire COI as a combined JSON file.
 *
 *
 */
class FetchSchedulesJSONDataUseCase(
    private val repository: AppRepository
) :
    UseCase<Unit, List<SchedulesResponse>> {

    override suspend fun invoke(params: Unit): Result<List<SchedulesResponse>> {
        return repository.getSchedulesResponse().toResult()

    }
}
