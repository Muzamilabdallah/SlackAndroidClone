package com.praxis.feat.onboarding.ui.exceptions

import com.praxis.feat.onboarding.ui.model.FailureType

class FormValidationFailed(val failType: FailureType) : Throwable()