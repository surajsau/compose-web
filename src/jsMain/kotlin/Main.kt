
import androidx.compose.runtime.mutableStateOf
import androidx.compose.web.attributes.rows
import androidx.compose.web.css.AlignContent
import androidx.compose.web.css.AlignItems
import androidx.compose.web.css.AlignSelf
import androidx.compose.web.css.Color
import androidx.compose.web.css.FlexDirection
import androidx.compose.web.css.FlexWrap
import androidx.compose.web.css.JustifyContent
import androidx.compose.web.css.alignContent
import androidx.compose.web.css.alignItems
import androidx.compose.web.css.alignSelf
import androidx.compose.web.css.backgroundColor
import androidx.compose.web.css.color
import androidx.compose.web.css.flexFlow
import androidx.compose.web.css.justifyContent
import androidx.compose.web.css.marginLeft
import androidx.compose.web.css.marginTop
import androidx.compose.web.css.padding
import androidx.compose.web.css.percent
import androidx.compose.web.css.px
import androidx.compose.web.css.width
import androidx.compose.web.elements.Button
import androidx.compose.web.elements.Div
import androidx.compose.web.elements.Text
import androidx.compose.web.elements.TextArea
import androidx.compose.web.renderComposable

fun main() {

    val text = mutableStateOf("0")

    var edit = ""

    renderComposable(rootElementId = "root") {
        Div(style = {
            padding(25.px)
            justifyContent(JustifyContent.SpaceBetween)
            flexFlow(FlexDirection.Row, FlexWrap.Nowrap)
        }) {
            Div(
                style = {
                    width(50.percent)
                    flexFlow(FlexDirection.Column, FlexWrap.Nowrap)
                    alignContent(AlignContent.Center)
                }
            ) {
                TextArea(
                    attrs = {
                        rows(7)
                        onTextInput { edit = it.inputValue }
                    },
                    style = {
                        width(100.percent)
                    },
                    value = ""
                )

                Button(
                    attrs = { onClick { text.value = handleText(edit) } },
                    style = {
                        backgroundColor(Color.invoke("#1da1f2"))
                        color(Color.invoke("#FFFFFF"))
                        justifyContent(JustifyContent.Center)
                        alignItems(AlignItems.Stretch)
                    }
                ) {
                    Div (
                        style = {
                            width(100.percent)
                            marginLeft(20.px)
                            marginTop(5.px)
                            alignContent(AlignContent.Center)
                        }
                    ) {
                        Text("Submit")
                    }
                }
            }

            Div(
                style = {
                    width(50.percent)
                    alignSelf(AlignSelf.Stretch)
                }
            ) {
                Text(text.value)
            }
        }
    }
}

private fun handleText(value: String): String {
    val components = value.split("\n").map { it.split(",") }

    val total = components.map { it[1].toLong() }.reduce { acc, componentValue -> acc + componentValue }

    return components
        .map { listOf(it[0], process(it[1].toLong(), total)) }
        .joinToString(separator = "\n") { "${it[0]}: ${it[1]}" }
}

private fun process(value: Long, total: Long): String {
    val percent = (value * 100)/total
    return (1..(percent/10)).joinToString(separator = "") { "▉" }
        .plus(if (percent % 10L != 0L) "▍" else "")
}