package id.aasumitro.finalsubmission

import android.content.Context
import id.aasumitro.finalsubmission.data.model.DataResult
import id.aasumitro.finalsubmission.data.source.Repository
import id.aasumitro.finalsubmission.data.source.remote.ApiClient
import io.reactivex.observers.TestObserver
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RemoteDataTest {

    companion object {
        const val LEAGUE_ID_TEST = 4328
        const val LEAGUE_NAME_TEST = "English Premier League"
        const val EVENT_ID_TEST = 576511
        const val MATCH_BY_NAME_TEST = "Arsenal_vs_Chelsea"
        const val TEAM_NAME_TEST = "Chelsea"
        const val TEAM_ID_TEST = 133610
    }

    @Mock private lateinit var mMockContext: Context
    @Mock private lateinit var mRepository: Repository

    @Before
    @Throws(Exception::class) fun setUp() {
        MockitoAnnotations.initMocks(this)
        mRepository = Repository(mMockContext)
    }

    @Test fun test_FetchPrevMatch_repo() {
        val result =
                mRepository.getLastMatch(LEAGUE_ID_TEST)
        val testObserver = TestObserver<DataResult.EventsResult>()
        result?.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        val listResult = testObserver.values()[0]
        assertEquals(15, listResult.matchList.size)
    }

    @Test fun test_FetchNextMatch_repo() {
        val result =
                mRepository.getNextMatch(LEAGUE_ID_TEST)
        val testObserver = TestObserver<DataResult.EventsResult>()
        result?.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        val listResult = testObserver.values()[0]
        assertEquals(15, listResult.matchList.size)
    }

    @Test fun test_FetchDetailMatch_repo() {
        val result =
                mRepository.getMatchDetail(EVENT_ID_TEST)
        val testObserver = TestObserver<DataResult.EventsResult>()
        result?.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        val listResult = testObserver.values()[0]
        assertEquals(1, listResult.matchList.size)
        assertEquals("Huddersfield Town", listResult.matchList[0].homeName)
        assertEquals("Crystal Palace", listResult.matchList[0].awayName)
    }

    @Test fun test_FetchMatchByName_repo() {
        val result =
                mRepository.getMatchByName(MATCH_BY_NAME_TEST)
        val testObserver = TestObserver<DataResult.EventResult>()
        result?.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        val listResult = testObserver.values()[0]
        assertEquals("Arsenal", listResult.matchList[0].homeName)
        assertEquals("Chelsea", listResult.matchList[0].awayName)
    }

    @Test fun test_FetchTeams_repo() {
        val result =
                mRepository.getRemoteTeams(LEAGUE_NAME_TEST)
        val testObserver = TestObserver<DataResult.TeamResult>()
        result?.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        val listResult = testObserver.values()[0]
        assertEquals(20, listResult.teamList.size)
        assertEquals("Arsenal", listResult.teamList[0].name)
    }

    @Test fun test_FetchTeamDetail_repo() {
        val result =
                mRepository.getTeamDetail(TEAM_ID_TEST)
        val testObserver = TestObserver<DataResult.TeamResult>()
        result?.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        val listResult = testObserver.values()[0]
        assertEquals(1, listResult.teamList.size)
        assertEquals("1905", listResult.teamList[0].formedYear)
    }

    @Test fun test_FetchTeamByName_repo() {
        val result =
                mRepository.getTeamByName(TEAM_NAME_TEST)
        val testObserver = TestObserver<DataResult.TeamResult>()
        result?.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        val listResult = testObserver.values()[0]
        assertEquals("Chelsea", listResult.teamList[0].name)
        assertEquals("1905", listResult.teamList[0].formedYear)
    }

    @Test fun test_FetchPlayers_Repo() {
        val result =
                mRepository.getPlayers(TEAM_ID_TEST)
        val testObserver = TestObserver<DataResult.PlayerResult>()
        result?.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        val listResult = testObserver.values()[0]
        assertEquals(24, listResult.playerList.size)
        assertEquals("Eden Hazard", listResult.playerList[8].name)
    }


}
