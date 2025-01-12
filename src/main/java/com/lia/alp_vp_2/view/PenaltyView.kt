package com.lia.alp_vp_2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.lia.alp_vp_2.R
import com.lia.alp_vp_2.enum.PagesEnum
import com.lia.alp_vp_2.model.PenaltyModelItem
import com.lia.alp_vp_2.viewModel.PenaltyViewModel

@Composable
fun PenaltyView(
    navController: NavController? = null,
    viewModel: PenaltyViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val penalties by viewModel.penalties.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Restaurant List",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))


        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            items(penalties) { penalty ->
                PenaltyCard(
                    penalty = penalty,
                    onCardClick = {
                        navController?.navigate("${PagesEnum.PenaltyCard.name}/${penalty.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun PenaltyCard(
    penalty: PenaltyModelItem, onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(5.dp, RoundedCornerShape(15.dp))
            .clip(RoundedCornerShape(15.dp))
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(15.dp)),
        onClick = onCardClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .aspectRatio(1f)
                    .shadow(5.dp, RoundedCornerShape(15.dp))
                    .clip(RoundedCornerShape(15.dp))
                    .border(2.dp, Color.White, shape = RoundedCornerShape(15.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.blank_image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.padding(top = 10.dp)) {
                Text(
                    text = penalty.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = penalty.description,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    text = "${penalty.date}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PenaltyViewPreview() {
    PenaltyView()
}