package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
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

fun allCategories() = listOf(Category.POPULAR, Category.TOP_RATED, Category.UPCOMING)

@Composable
fun CategoryRow(selectedCategory: Category, onCategorySelected: (Category) -> Unit) {
    val categories = allCategories()
    ScrollableRow(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
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
    Column(modifier = modifier.clickable(onClick = onClick)) {
        Text(
            text = category.value,
            modifier = Modifier.padding(bottom = 8.dp),
            color = if (selected)
                MaterialTheme.colors.onSurface
            else
                MaterialTheme.colors.onSurface.copy(alpha = 0.4F),
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight(600))
        )
        Box(
            modifier = Modifier
                .height(6.dp)
                .width(42.dp)
                .clip(RoundedCornerShape(percent = 50))
                .background(color = if (selected) MaterialTheme.colors.primaryVariant else Color.Transparent)
        )
    }
}