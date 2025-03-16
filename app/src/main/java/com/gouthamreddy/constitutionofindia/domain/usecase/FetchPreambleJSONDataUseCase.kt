package com.gouthamreddy.constitutionofindia.domain.usecase

import com.gouthamreddy.constitutionofindia.data.models.PreambleResponse
import com.gouthamreddy.constitutionofindia.domain.AppRepository
import com.gouthamreddy.constitutionofindia.domain.UseCase
import com.gouthamreddy.constitutionofindia.domain.toResult

/**
 * We are fetching entire COI as a combined JSON file.
 *
 *
 */
class FetchPreambleJSONDataUseCase(
    private val repository: AppRepository
) :
    UseCase<Unit, PreambleResponse> {

    override suspend fun invoke(params: Unit): Result<PreambleResponse> {
        return repository.getPreambleResponse().toResult()

    }
}
