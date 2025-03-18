package com.gouthamreddy.constitutionofindia.domain.usecase

import com.gouthamreddy.constitutionofindia.data.models.ConstitutionCombinedResponseItem
import com.gouthamreddy.constitutionofindia.domain.AppRepository
import com.gouthamreddy.constitutionofindia.domain.UseCase

/**
 * We are fetching entire COI as a combined JSON file.
 *
 *
 */
class FetchCombinedJSONDataUseCase(
    private val repository: AppRepository
) :
    UseCase<Unit, List<ConstitutionCombinedResponseItem>> {

    override suspend fun invoke(params: Unit): Result<List<ConstitutionCombinedResponseItem>> {
        return repository.getCombinedResponse()
    }
}
