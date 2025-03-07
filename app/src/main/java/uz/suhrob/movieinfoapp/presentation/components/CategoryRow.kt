package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

enum class Category(val value: String) {
    POPULAR("Popular"),
    TOP_RATED("Top Rated"),
    UPCOMING("Upcoming")
}

@Composable
fun CategoryRow(selectedCategory: Category, onCategorySelected: (Category) -> Unit) {
    val categories = Category.entries
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        categories.forEach { category ->
            CategoryItem(
                category = category,
                modifier = Modifier.padding(horizontal = 16.dp),
                selectedCategory == category
            ) {
                onCategorySelected(category)
            }
        }
    }
}

@Composable
fun CategoryItem(category: Category, modifier: Modifier, selected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = modifier.clickable(
            onClick = onClick,
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        )
    ) {
        Text(
            text = category.value,
            modifier = Modifier.padding(bottom = 8.dp),
            color = if (selected)
                MaterialTheme.colorScheme.onSurface
            else
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4F),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight(600))
        )
        Box(
            modifier = Modifier
                .height(6.dp)
                .width(42.dp)
                .clip(RoundedCornerShape(percent = 50))
                .background(
                    color = if (selected) MaterialTheme.colorScheme.secondary
                    else Color.Transparent,
                )
        )
    }
}