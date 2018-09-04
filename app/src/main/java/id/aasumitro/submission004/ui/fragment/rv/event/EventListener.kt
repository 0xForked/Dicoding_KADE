package id.aasumitro.submission004.ui.fragment.rv.event

import id.aasumitro.submission004.data.models.Match

interface EventListener {

    fun onEventPressed(match: Match?)

}