package com.mattsplat.tfa_compose

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mattsplat.tfa_compose.DataModels.Account
import com.mattsplat.tfa_compose.ViewModels.DashboardViewModel

@Composable
fun DashboardScreen(navController: NavHostController, viewModel: DashboardViewModel) {
    val context = LocalContext.current
    val accountList = remember { mutableStateListOf<Account>() }

    val listener = object:  RequestListener {
        override fun onError(error: String?) {
            Toast.makeText(
                context,
                "Error loading Accounts",
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun onResponse(response: Any?) {
            accountList.clear()
            for(item in response as ArrayList<*>) {
                accountList.add(item as Account)
            }
        }
    }
    val apiService = ApiService(listener = listener)
    apiService.accounts()

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
            .padding(top = 100.dp)
    ) {
        Text(
            text = "Accounts",
            fontSize = 48.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 25.dp)
            )
        val myData = listOf("account 1,", "Account 2")
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {
            items(accountList) { item ->
                Card(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    elevation = 2.dp,
                    backgroundColor = Color.White,
                    shape = RoundedCornerShape(corner = CornerSize(16.dp))
                ) {
                    Row(
                        Modifier.clickable { navController.navigate("account/" + item.id) },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.owl_icon),
                            contentDescription = "Mr Owl",
                            modifier = Modifier.height(60.dp)
                        )
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                                .align(Alignment.CenterVertically)
                        ) {
                            Text(text = item.name, style = MaterialTheme.typography.h6)
                            Text(
                                text = "VIEW DETAIL",
                                style = MaterialTheme.typography.caption
                            )
                        }
                    }
                }
            }
        }
    }
}