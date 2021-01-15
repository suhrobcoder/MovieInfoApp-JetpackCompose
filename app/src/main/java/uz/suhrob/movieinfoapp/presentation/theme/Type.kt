package uz.suhrob.movieinfoapp.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.font
import androidx.compose.ui.text.font.fontFamily
import androidx.compose.ui.unit.sp
import uz.suhrob.movieinfoapp.R

private val SFProFont = fontFamily(
    font(R.font.sf_pro_regular, FontWeight.Normal),
    font(R.font.sf_pro_medium, FontWeight.Medium),
    font(R.font.sf_pro_semibold, FontWeight.SemiBold),
    font(R.font.sf_pro_bold, FontWeight.Bold),
)

val SFProTypography = Typography(
    defaultFontFamily = SFProFont
)