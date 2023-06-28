package ru.kvf.emptyproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.kvf.emptyproject.ui.theme.EmptyProjectTheme


data class Item(
    val title: String,
    val text: String,
    val imageRes: Int,
    val viewType: ViewTypes
)

class MainActivity : ComponentActivity() {

    private lateinit var rv: RecyclerView

    private val picsArray = arrayOf(
        R.drawable.a,
        R.drawable.b,
        R.drawable.c,
        R.drawable.d,
        R.drawable.e,
    )

    val itemsFlow = MutableStateFlow<List<Item>>(emptyList())

    val items = listOf(
        Item(
            title = " поля саммита НАТО, пожаловался Кулеба",
            text = "ВАШИНГТОН, 28 июн — РИА ",
            imageRes = getRandomPic(),
            viewType = getRandomViewType()
        ),
        Item(
            title = "Мы  а принять участие в совете НАТО — Ук",
            text = "Некрямо противоположного мнения, — заявил министр.",
            imageRes = getRandomPic(),
            viewType = getRandomViewType()
        ),
        Item(
            title = "Users of Vhen updating to this version of RecyclerView to avoid",
            text = "Tonger an improvement over the default state.",
            imageRes = getRandomPic(),
            viewType = getRandomViewType()
        ),
        Item(
            title = "Мы получили приглашение, но не вете НАТО — Ук",
            text = "Некоторые страны до заявил министр.",
            imageRes = getRandomPic(),
            viewType = getRandomViewType()
        ),
        Item(
            title = "Украину пригласили лишь на поля саммита НАТО, пожаловался Кулеба",
            text = "ВАШИНГТО во встрече на полях июльского саммита НАТО в Вильнюсе, заявил глава украинского МИД Дмитрий Кулеба в интервью CNN.",
            imageRes = getRandomPic(),
            viewType = getRandomViewType()
        ),
        Item(
            title = "Users of ViewPager2 must f RecyclerView to avoid",
            text = "This release contains performance e uing the MyComposeAda an improvement over the default state.",
            imageRes = getRandomPic(),
            viewType = getRandomViewType()
        ),
        Item(
            title = "Мы получили приглашение, но не на сам саммит, а принять участие в совете НАТО — Ук",
            text = "Некоторые странтивоположного мнения, — заявил министр.",
            imageRes = getRandomPic(),
            viewType = getRandomViewType()
        ),
        Item(
            title = "Users of ViewPager2 must update to at leto this version of RecyclerView to avoid",
            text = "Tre using the MyCom the previouse no longer an improvement over the default state.",
            imageRes = getRandomPic(),
            viewType = getRandomViewType()
        ),
        Item(
            title = "Мы получили приглашение, но не на сам саммит, а принять участие в совете НАТО — Ук",
            text = "Некоторые страны доказываютскалации. Мы придерживаемся прямо противоположного мнения, — заявил министр.",
            imageRes = getRandomPic(),
            viewType = getRandomViewType()
        ),
        Item(
            title = "Украину пригласили я саммита НАТО, пожаловался Кулеба",
            text = "ВАШИНГТОН, 28 июн — РИА Новости. Украину пригласили принять участие только й Кулеба в интервью CNN.",
            imageRes = getRandomPic(),
            viewType = getRandomViewType()
        ),
        Item(
            title = "Users of ViewPager2 must update to at lhis version of RecyclerView to avoid",
            text = "This release contaen uster and DisposeOnViewTe no longer an improvement over the default state.",
            imageRes = getRandomPic(),
            viewType = getRandomViewType()
        )
    )

    private fun getRandomPic(): Int = picsArray.random()

    private fun getRandomViewType(): ViewTypes = ViewTypes.values().random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv = findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)
        val adapter = Adapter()
        rv.adapter = adapter

        lifecycleScope.launch {
            itemsFlow.collect { list ->
                adapter.submitList(list)
            }
        }

        lifecycleScope.launch {
            while (isActive) {
                delay(3000)
                val newList = items.shuffled()
                itemsFlow.value = newList
            }
        }

    }
}

val diffCallBack = object : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}

class Adapter : ListAdapter<Item, Adapter.ViewHolder>(diffCallBack) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val text: TextView = view.findViewById(R.id.text)
        val pic: ImageView = view.findViewById(R.id.img)
        fun bind(item: Item) {
            title.text = item.text
            text.text = item.text
            pic.setImageResource(item.imageRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layout = when (viewType) {
            0 -> R.layout.rv_item_red
            1 ->  R.layout.rv_item_blue
            else -> R.layout.rv_item_green
        }
        return ViewHolder(inflater.inflate(layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return item.viewType.ordinal
    }


}

enum class ViewTypes {
    RED, BLUE, GREEN
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EmptyProjectTheme {
        Greeting("Android")
    }
}