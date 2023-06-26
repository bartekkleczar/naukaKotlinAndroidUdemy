package pl.klenczi.unstructuredconcurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserDataManager1 {
    suspend fun getTotalUserCount(): Int{
        var count = 0
        CoroutineScope(Dispatchers.IO).launch{
            delay(1000)
            count = 50
        }

        val deffered = CoroutineScope(Dispatchers.IO).async {
            delay(3000)
            return@async 70
        }

        return count + deffered.await()
    }
}