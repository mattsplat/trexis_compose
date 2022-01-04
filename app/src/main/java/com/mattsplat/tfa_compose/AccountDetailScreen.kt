package com.mattsplat.tfa_compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mattsplat.tfa_compose.ViewModels.AccountDetailViewModel
import java.text.DecimalFormat

@Composable
fun AccountDetailScreen(navController: NavHostController, account_id: Int, viewModel: AccountDetailViewModel) {
    val transactionsList by viewModel.transactions.observeAsState()
    viewModel.requestTransactions(account_id.toString())

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
    ) {
        Icon(
            Icons.Rounded.ArrowBack,
            contentDescription = "Localized description",
            modifier = Modifier
                .size(75.dp)
                .align(Alignment.Start)
                .padding(start = 25.dp, top = 25.dp, bottom = 30.dp)
                .clickable { navController.navigate("dashboard") }
        )

        Card(
            modifier = Modifier
                .padding(top = 25.dp, bottom = 25.dp)
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.15f),
            elevation = 3.dp,
            backgroundColor = Color.White,
            shape = RoundedCornerShape(corner = CornerSize(16.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Account: " + account_id.toString(),
                    fontSize = 48.sp,
                    textAlign = TextAlign.Center,
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {
            if(transactionsList !== null) {
                items(transactionsList!!) { item ->
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        elevation = 2.dp,
                        backgroundColor = Color.White,
                        shape = RoundedCornerShape(corner = CornerSize(16.dp))
                    ) {
                        Row(
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
                                Text(text = item.title, style = MaterialTheme.typography.h6)
                                Text(
                                    text = formatBalance(item.balance),
                                    style = MaterialTheme.typography.caption,
                                    modifier = Modifier
                                        .align(Alignment.End)
                                        .padding(end = 10.dp, top = 10.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}

fun formatBalance(balance: String): String {
    val m: Float = balance.toFloat()
    val formatter = DecimalFormat.getCurrencyInstance()
    return formatter.format(m)
}
