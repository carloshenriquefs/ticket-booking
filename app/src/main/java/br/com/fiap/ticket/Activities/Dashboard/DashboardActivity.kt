package br.com.fiap.ticket.Activities.Dashboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import br.com.fiap.ticket.Activities.SearchResult.SearchResultActivity
import br.com.fiap.ticket.Activities.Splash.GradientButton
import br.com.fiap.ticket.Activities.Splash.StatusTopBarColor
import br.com.fiap.ticket.Domain.LocationModel
import br.com.fiap.ticket.R
import br.com.fiap.ticket.ViewModel.MainViewModel

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@Composable
@Preview
fun MainScreen() {
    val locations = remember { mutableStateListOf<LocationModel>() }
    val viewModel = MainViewModel()
    var showLocationLoading by remember { mutableStateOf(true) }
    var from: String = ""
    var to: String = ""
    var classes: String = ""
    var adultPassgener: String = ""
    var childPassgener: String = ""
    val context= LocalContext.current

    StatusTopBarColor()

    LaunchedEffect(Unit) {
        viewModel.loadLocations().observeForever { result ->
            locations.clear()
            locations.addAll(result)
            showLocationLoading = false
        }
    }

    Scaffold(
        bottomBar = { MyBottomBar() },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.darkPurple2))
                .padding(paddingValues = paddingValues)
        ) {
            item {
                TopBar()
            }
            item {
                Column(
                    modifier = Modifier
                        .padding(32.dp)
                        .background(
                            colorResource(R.color.darkPurple),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 24.dp)
                ) {
                    YellowTitle("From")
                    val locationNames: List<String> = locations.map { it.Name }

                    DropDownList(
                        items = locationNames,
                        loadingIcon = painterResource(R.drawable.from_ic),
                        hint = "Select origin",
                        showLocationLoading = showLocationLoading
                    ) { selectedItem ->
                        from = selectedItem
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    YellowTitle("To")

                    DropDownList(
                        items = locationNames,
                        loadingIcon = painterResource(R.drawable.from_ic),
                        hint = "Select Destination",
                        showLocationLoading = showLocationLoading
                    ) { selectedItem ->
                        from = selectedItem
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    YellowTitle("Passengers")
                    Row(modifier = Modifier.fillMaxWidth()) {
                        PassengerCounter(
                            title = "Adult",
                            modifier = Modifier.weight(1f),
                            onItemSelected = { adultPassgener = it }
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        PassengerCounter(
                            title = "Child",
                            modifier = Modifier.weight(1f),
                            onItemSelected = { childPassgener = it }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        YellowTitle("Departure date", Modifier.weight(1f))
                        Spacer(modifier = Modifier.width(16.dp))
                        YellowTitle("Return date", Modifier.weight(1f))
                    }

                    DatePickerScreen(Modifier.weight(1f))

                    Spacer(modifier = Modifier.height(16.dp))

                    YellowTitle("class")
                    val classItems = listOf("Business class", "First class", "Economy Class")

                    DropDownList(
                        items = classItems,
                        loadingIcon = painterResource(R.drawable.seat_black_ic),
                        hint = "Select class",
                        showLocationLoading = showLocationLoading
                    ) { selectedItem ->
                        to = selectedItem
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    GradientButton(
                        onClick = {
                            val intent= Intent(context, SearchResultActivity::class.java).apply {
                                putExtra("from", from)
                                putExtra("from", from)
                                putExtra("from", from)
                            }
                            startActivity(context, intent, null)
                        },
                        text = "Search",
                    )
                }
            }
        }
    }
}

@Composable
fun YellowTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontWeight = FontWeight.SemiBold,
        color = colorResource(R.color.orange),
        modifier = modifier
    )
}