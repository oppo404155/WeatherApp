import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import com.example.todo_app.weatherFeatures.Domain.models.HourlyWeatherType
import com.example.todo_app.weatherFeatures.presentation.home.hourly.HourlyWeatherFilter

@Composable
fun HourlyWeatherFilters(
    selectedFilter: HourlyWeatherType,
    onFilterSelected: (HourlyWeatherType) -> Unit
) {
    val items = HourlyWeatherType.values().asList()

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(items) { Index, item ->
            HourlyWeatherFilter(
                item = item,
                selected = item == selectedFilter,
                onFilterSelected = onFilterSelected
            )
        }
    }
}