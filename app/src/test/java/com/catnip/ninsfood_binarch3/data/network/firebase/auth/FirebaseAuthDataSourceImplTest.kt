package com.catnip.ninsfood_binarch3.data.network.firebase.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FirebaseAuthDataSourceImplTest {

    @MockK(relaxed = true)
    lateinit var firebaseAuth: FirebaseAuth

    private lateinit var dataSource: FirebaseAuthDataSource

    val firebaseUserMock = mockk<FirebaseUser>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
    }

    private fun mockTaskVoid(exception: Exception? = null): Task<Void> {
        val task: Task<Void> = mockk(relaxed = true)
        every { task.isComplete } returns true
        every { task.exception } returns exception
        every { task.isCanceled } returns false
        val relaxedVoid: Void = mockk(relaxed = true)
        every { task.result } returns relaxedVoid
        return task
    }

    private fun mockTaskAuthResult(exception: Exception? = null): Task<AuthResult> {
        val task: Task<AuthResult> = mockk(relaxed = true)
        every { task.isComplete } returns true
        every { task.exception } returns exception
        every { task.isCanceled } returns false
        val relaxedResult: AuthResult = mockk(relaxed = true)
        every { task.result } returns relaxedResult
        every { task.result.user } returns mockk(relaxed = true)
        return task
    }

    @Test
    fun `test do login`() {
        every { firebaseAuth.signInWithEmailAndPassword(any(), any()) } returns mockTaskAuthResult()
        runTest {
            val result = dataSource.doLogin("email", "password")
            Assert.assertEquals(result, true)
        }
    }

    @Test
    fun `test is logged in`() {
        every { firebaseAuth.currentUser } returns firebaseUserMock
        runTest {
            val result = dataSource.isLoggedIn()
            Assert.assertEquals(result, true)
        }
    }

    @Test
    fun `test update password`() {
        coEvery { firebaseAuth.currentUser?.updatePassword(any()) } returns mockTaskVoid()
        runTest {
            val result = dataSource.updatePassword("update password")
            Assert.assertEquals(result, true)
        }
    }

    @Test
    fun `test update email`() {
        coEvery { firebaseAuth.currentUser?.updateEmail(any()) } returns mockTaskVoid()
        runTest {
            val result = dataSource.updateEmail("update email")
            Assert.assertEquals(result, true)
        }
    }

    @Test
    fun `test send change password request by email`() {
        coEvery { firebaseAuth.currentUser?.email } returns ""
        runTest {
            val result = dataSource.sendChangePasswordRequestByEmail()
            Assert.assertEquals(result, true)
        }
    }

    @Test
    fun `test get current user`() {
        every { firebaseAuth.currentUser } returns firebaseUserMock
        runTest {
            val result = dataSource.getCurrentUser()
            Assert.assertEquals(result, firebaseUserMock)
        }
    }

    @Test
    fun `test logout`() {
        mockkStatic(FirebaseAuth::class)
        every { FirebaseAuth.getInstance() } returns firebaseAuth
        every { firebaseAuth.signOut() } returns Unit
        val result = dataSource.doLogout()
        Assert.assertEquals(result, true)
    }
}
