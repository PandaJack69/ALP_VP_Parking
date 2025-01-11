package com.example.alp_vp.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp_vp.R

@Composable
fun HomePage(hasReservation: Boolean = false, reservedSpot: String? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Greeting Section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 25.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Hello,",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Igny!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Image(
                painter = painterResource(R.drawable.romy),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(100.dp))
            )
        }

        // Traffic Information Section
        Column(
            modifier = Modifier.padding(top = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Today's Traffic",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            // Progress bar
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val progress = 0.79f

                val progressText = when {
                    progress <= 0.35f -> "Low Traffic"
                    progress <= 0.70f -> "Crowded"
                    else -> "Very Crowded"
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize(), // Pastikan Box mengisi seluruh ruang yang tersedia
                    contentAlignment = Alignment.Center // Semua konten di tengah vertikal dan horizontal
                ) {
                    HalfCircleProgressBar(
                        progress = progress,
                        strokeWidth = 16.dp,
                        modifier = Modifier.size(200.dp) // Ukuran progress bar disesuaikan
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.align(Alignment.Center) // Memastikan Column juga di tengah
                    ) {
                        Text(
                            text = "${(progress * 100).toInt()}%",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = progressText,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

            }
        }

        // Feature Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(173.dp, 280.dp)
                    .shadow(elevation = 10.dp, shape = RoundedCornerShape(38.dp))
                    .background(Color(0xFF57E798), RoundedCornerShape(38.dp))
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.car),
                        contentDescription = null,
                        modifier = Modifier.size(65.dp)
                    )
                    Text(
                        text = "Reservation",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Reserve a parking spot here.",
                        fontSize = 13.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

            }
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(173.dp, 135.dp)
                        .shadow(elevation = 10.dp, shape = RoundedCornerShape(38.dp))
                        .background(Color(0xFFFFA155), RoundedCornerShape(38.dp))
                        .padding(15.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.report),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                        Text(
                            text = "Reports",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Report improper parking here.",
                            fontSize = 13.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(173.dp, 135.dp)
                        .shadow(elevation = 10.dp, shape = RoundedCornerShape(38.dp))
                        .background(Color(0xFFEE8A70), RoundedCornerShape(38.dp))
                        .padding(15.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.penalty),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                        Text(
                            text = "Penalties",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "You have 0 penalties.",
                            fontSize = 13.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Reservation Section
        ReservationSection(hasReservation = hasReservation, reservedSpot = reservedSpot)
    }
}

@Composable
fun ReservationSection(hasReservation: Boolean, reservedSpot: String?) {
    Column(
        modifier = Modifier
            .padding(5.dp)
            .padding(top = 10.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Your Reservation",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 10.dp)
        )
        if (hasReservation && reservedSpot != null) {
            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(25.dp)
                    )
                    .background(Color(0xFF6BC4F7), shape = RoundedCornerShape(25.dp))
                    .fillMaxWidth()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Column(
                        modifier = Modifier.padding(end = 30.dp)
                    ){
                        Text(
                            text = "Time Remaining",
                            fontSize = 22.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "04:38",
                            fontSize = 22.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                        Text(
                            text = "View for more details",
                            fontSize = 13.sp,
                            color = Color.White,
                            modifier = Modifier.padding(top = 20.dp)
                        )
                    }
                    Image(
                        painter = painterResource(R.drawable.stopwatch),
                        contentDescription = "time",
                        modifier = Modifier
                            .shadow(
                                elevation = 50.dp,
                                shape = RoundedCornerShape(50.dp)
                            )
                            .size(100.dp)
                    )
                }
            }
        } else {
            Image(
                painter = painterResource(R.drawable.car_gray),
                contentDescription = "no reservation",
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "You have no reservation.",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun HalfCircleProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 16.dp,
    backgroundColor: Color = Color.LightGray
) {
    val progressColor = when {
        progress <= 0.35f -> Color(0xFF49E654) // 0% - 35%
        progress <= 0.70f -> Color(0xFFF2E447) // 36% - 70%
        else -> Color(0xFFE64949) // > 70%
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Canvas(
            modifier = Modifier
                .size(200.dp)
                .offset(y = 30.dp)
        ) {
            val diameter = size.minDimension
            val startAngle = 180f
            val sweepAngle = 180f * progress

            drawArc(
                color = backgroundColor,
                startAngle = startAngle,
                sweepAngle = 180f,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )

            drawArc(
                color = progressColor,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePagePreview() {
    HomePage(hasReservation = true, reservedSpot = "A12")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePageNoReservationPreview() {
    HomePage(hasReservation = false)
}
