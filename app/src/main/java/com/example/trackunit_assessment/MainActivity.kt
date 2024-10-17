package com.example.trackunit_assessment

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.trackunit_assessment.model.Order
import com.example.trackunit_assessment.model.Product
import com.example.trackunit_assessment.ui.theme.TrackUnit_AssessmentTheme

import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrackUnit_AssessmentTheme {

                val viewModel: MainViewModel = remember { MainViewModel() }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    OrdersScreen(innerPadding, viewModel)
                }
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(padding: PaddingValues, viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {

            viewModel.getOrderData()

    }


    var selectedOrder by remember { mutableStateOf<Order?>(null) }
    val bottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    Log.i("TAGabs", "OrdersScreen: " + uiState)
    when (uiState) {

        is StateOrder.Idle -> {}
        is StateOrder.Loading -> {
            LoadingView()
        }

        is StateOrder.Data -> {
            Box(modifier = Modifier.padding(padding)){
                OrdersList((uiState as StateOrder.Data).data) { order ->
                    selectedOrder = order
                    coroutineScope.launch {
                        if (!bottomSheetState.isVisible) {
                            bottomSheetState.show()
                        }
                    }
                }
            }
        }
    }

    if (selectedOrder != null) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    bottomSheetState.hide()
                    selectedOrder = null
                }
            },
            sheetState = bottomSheetState
        ) {
            ProductsScreen(products = selectedOrder!!.items)
        }
    }
}


@Composable
fun LoadingView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun OrdersList(orders: List<Order>, onOrderClick: (Order) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(orders.size) { index ->
            OrderItem(order = orders[index], onOrderClick = onOrderClick)
        }
    }
}


@Composable
fun OrderItem(order: Order, onOrderClick: (Order) -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onClick = {
            onOrderClick(order)
        },

        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${order.items.size} Product in Order ${order.orderId}",
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = order.totalPrice.toString(),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}


@Composable
fun ProductsScreen(products: List<Product>) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(products.size) { index ->
            ProductItem(product = products[index])
        }
    }

}

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${product.id}",
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = product.name,
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = product.price.toString(),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

