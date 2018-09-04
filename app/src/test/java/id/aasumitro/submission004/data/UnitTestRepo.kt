package id.aasumitro.submission004.data

import android.content.Context
import id.aasumitro.submission004.data.models.Results
import id.aasumitro.submission004.data.sources.EventRepository
import id.aasumitro.submission004.data.sources.remote.ApiClient
import id.aasumitro.submission004.ui.activity.splash.SplashViewModel
import id.aasumitro.submission004.ui.fragment.ListViewModel
import id.aasumitro.submission004.util.AppConst.FAVORITE_MATCH
import id.aasumitro.submission004.util.AppConst.LEAGUE
import id.aasumitro.submission004.util.AppConst.LEAGUE_ID
import id.aasumitro.submission004.util.AppConst.NEXT_MATCH
import id.aasumitro.submission004.util.AppConst.PREV_MATCH
import io.reactivex.observers.TestObserver
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class UnitTestRepo {

    @Mock private lateinit var mMockContext: Context
    @Mock private lateinit var mApiClient: ApiClient
    @Mock private lateinit var mRepository: EventRepository

    @Before @Throws(Exception::class) fun setUp() {
        MockitoAnnotations.initMocks(this)
        mApiClient = ApiClient()
        mRepository = EventRepository(mMockContext, mApiClient)
    }

    @Test fun test_LastMatchFromServer_repo() {
        val result = mRepository.getLastMatch(LEAGUE_ID)
        val testObserver = TestObserver<Results.MatchResult>()
        result?.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        val listResult = testObserver.values()[0]
        assertEquals(15, listResult.eventList.size)
    }

    @Test fun test_NextMatchFromServer_repo() {
        val result = mRepository.getNextMatch(LEAGUE_ID)
        val testObserver = TestObserver<Results.MatchResult>()
        result?.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        val listResult = testObserver.values()[0]
        assertEquals(15, listResult.eventList.size)
    }

    @Test fun test_DetailMatchFromServer_repo() {
        val testEventId = 576511
        val result = mRepository.getDetailMatch(testEventId)
        val testObserver = TestObserver<Results.MatchResult>()
        result?.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        val listResult = testObserver.values()[0]
        assertEquals(1, listResult.eventList.size)
        assertEquals("Huddersfield Town", listResult.eventList[0].homeName)
        assertEquals("Crystal Palace", listResult.eventList[0].awayName)
    }

    @Test fun test_TeamDataFromServer_repo() {
        val result = mRepository.getTeamData(LEAGUE)
        val testObserver = TestObserver<Results.TeamResult>()
        result?.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        val listResult = testObserver.values()[0]
        assertEquals(20, listResult.teamList.size)
        assertEquals("Arsenal", listResult.teamList[0].name)
    }

    @Test fun test_SplashScreen_task() {
        val c = Mockito.mock(SplashViewModel::class.java)
        c.startTask()
        verify(c).startTask()
    }

    @Test fun test_ListVM_task() {
        val c = Mockito.mock(ListViewModel::class.java)

        // test and verify method/function
        // from listVM is called
        // simple test

        c.startTask(PREV_MATCH, LEAGUE_ID)
        verify(c).startTask(PREV_MATCH, LEAGUE_ID)
        c.startTask(NEXT_MATCH, LEAGUE_ID)
        verify(c).startTask(NEXT_MATCH, LEAGUE_ID)
        c.startTask(FAVORITE_MATCH, LEAGUE_ID)
        verify(c).startTask(FAVORITE_MATCH, LEAGUE_ID)
    }


}