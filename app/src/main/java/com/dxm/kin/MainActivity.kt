package com.dxm.kin

import android.app.Activity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import java.io.Serializable
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.functions
import kotlin.reflect.full.memberProperties


inline fun <reified T> printClass(tag: String) = Log.e(tag, "class = ${T::class}, null is ${T::class.simpleName}")

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        startActivity<ReaderActivity> {
            ReaderActivity::charSequenceArrayList willBe Extras.charSequenceArrayList
            ReaderActivity::charSequenceArrayListOptional willBe Extras.charSequenceArrayListOptional
            ReaderActivity::intArrayList willBe Extras.intArrayList
            ReaderActivity::intArrayListOptional willBe Extras.intArrayListOptional
            ReaderActivity::stringArrayList willBe Extras.stringArrayList
            ReaderActivity::stringArrayListOptional willBe Extras.stringArrayListOptional
            ReaderActivity::parcelableArrayList willBe Extras.parcelableArrayList
            ReaderActivity::parcelableArrayListOptional willBe Extras.parcelableArrayListOptional
            ReaderActivity::boolean willBe Extras.boolean
            ReaderActivity::booleanArray willBe Extras.booleanArray
            ReaderActivity::booleanArrayOptional willBe Extras.booleanArrayOptional
            ReaderActivity::bundle willBe Extras.bundle
            ReaderActivity::bundleOptional willBe Extras.bundleOptional
            ReaderActivity::byte willBe Extras.byte
            ReaderActivity::byteArray willBe Extras.byteArray
            ReaderActivity::byteArrayOptional willBe Extras.byteArrayOptional
            ReaderActivity::char willBe Extras.char
            ReaderActivity::charArray willBe Extras.charArray
            ReaderActivity::charArrayOptional willBe Extras.charArrayOptional
            ReaderActivity::charSequence willBe Extras.charSequence
            ReaderActivity::double willBe Extras.double
            ReaderActivity::doubleArray willBe Extras.doubleArray
            ReaderActivity::doubleArrayOptional willBe Extras.doubleArrayOptional
            ReaderActivity::float willBe Extras.float
            ReaderActivity::floatArray willBe Extras.floatArray
            ReaderActivity::floatArrayOptional willBe Extras.floatArrayOptional
            ReaderActivity::int willBe Extras.int
            ReaderActivity::intArray willBe Extras.intArray
            ReaderActivity::intArrayOptional willBe Extras.intArrayOptional
            ReaderActivity::long willBe Extras.long
            ReaderActivity::longArray willBe Extras.longArray
            ReaderActivity::longArrayOptional willBe Extras.longArrayOptional
            ReaderActivity::parcelable willBe Extras.parcelable
            ReaderActivity::parcelableOptional willBe Extras.parcelableOptional
            ReaderActivity::stringArray willBe Extras.stringArray
            ReaderActivity::stringArrayOptional willBe Extras.stringArrayOptional
            ReaderActivity::charSequenceArray willBe Extras.charSequenceArray
            ReaderActivity::charSequenceArrayOptional willBe Extras.charSequenceArrayOptional
            ReaderActivity::parcelableArray willBe Extras.parcelableArray
            ReaderActivity::parcelableArrayOptional willBe Extras.parcelableArrayOptional
            ReaderActivity::serializable willBe Extras.serializable
            ReaderActivity::serializableOptional willBe Extras.serializableOptional
            ReaderActivity::short willBe Extras.short
            ReaderActivity::shortArray willBe Extras.shortArray
            ReaderActivity::shortArrayOptional willBe Extras.shortArrayOptional
            ReaderActivity::string willBe Extras.string
            ReaderActivity::stringOptional willBe Extras.stringOptional
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}

data class Box(val content: String) : Parcelable, Serializable {
    override fun writeToParcel(dest: Parcel, flags: Int) = dest.writeString(content)
    override fun describeContents() = 0
    override fun toString() = "Box($content)"

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Box> {
            override fun createFromParcel(source: Parcel) = Box(source.readString())
            override fun newArray(size: Int): Array<Box?> = Array(size) { null }
        }
    }
}

class ReaderActivity : Activity() {
    val charSequenceArrayList: ArrayList<CharSequence> by Kin.ArrayList<CharSequence>()
    val charSequenceArrayListOptional: ArrayList<CharSequence>? by Kin.ArrayList<CharSequence>().optional
    val intArrayList: ArrayList<Int> by Kin.ArrayList<Int>()
    val intArrayListOptional: ArrayList<Int>? by Kin.ArrayList<Int>().optional
    val stringArrayList: ArrayList<String> by Kin.ArrayList<String>()
    val stringArrayListOptional: ArrayList<String>? by Kin.ArrayList<String>().optional
    val parcelableArrayList: ArrayList<Box> by Kin.ArrayList<Box>()
    val parcelableArrayListOptional: ArrayList<Box>? by Kin.ArrayList<Box>().optional
    val boolean by Kin.Boolean(true)
    val booleanArray: BooleanArray by Kin.BooleanArray()
    val booleanArrayOptional: BooleanArray? by Kin.BooleanArray().optional
    val bundle: Bundle by Kin.Bundle()
    val bundleOptional: Bundle? by Kin.Bundle().optional
    val byte: Byte by Kin.Byte(0)
    val byteArray: ByteArray by Kin.ByteArray()
    val byteArrayOptional: ByteArray? by Kin.ByteArray().optional
    val char: Char by Kin.Char('t')
    val charArray: CharArray by Kin.CharArray()
    val charArrayOptional: CharArray? by Kin.CharArray().optional
    val charSequence: CharSequence by Kin.CharSequence()
    val double: Double by Kin.Double(0.0)
    val doubleArray: DoubleArray by Kin.DoubleArray()
    val doubleArrayOptional: DoubleArray? by Kin.DoubleArray().optional
    val float: Float by Kin.Float(0.0F)
    val floatArray: FloatArray by Kin.FloatArray()
    val floatArrayOptional: FloatArray? by Kin.FloatArray().optional
    val int: Int by Kin.Int(0)
    val intArray: IntArray by Kin.IntArray()
    val intArrayOptional: IntArray? by Kin.IntArray().optional
    val long: Long by Kin.Long(0)
    val longArray: LongArray by Kin.LongArray()
    val longArrayOptional: LongArray? by Kin.LongArray().optional
    val parcelable: Parcelable by Kin.Parcelable<Box>()
    val parcelableOptional: Parcelable? by Kin.Parcelable<Box>().optional
    val stringArray: Array<String> by Kin.Array<String>()
    val stringArrayOptional: Array<String>? by Kin.Array<String>().optional
    val charSequenceArray: Array<CharSequence> by Kin.Array<CharSequence>()
    val charSequenceArrayOptional: Array<CharSequence>? by Kin.Array<CharSequence>().optional
    val parcelableArray: Array<Box> by Kin.Array<Box>()
    val parcelableArrayOptional: Array<Box>? by Kin.Array<Box>()
    val serializable: Serializable by Kin.Serializable<Box>()
    val serializableOptional: Serializable? by Kin.Serializable<Box>().optional
    val short: Short by Kin.Short(0)
    val shortArray: ShortArray by Kin.ShortArray()
    val shortArrayOptional: ShortArray? by Kin.ShortArray().optional
    val string: String by Kin.String()
    val stringOptional: String? by Kin.String().optional

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        validate()
    }

    fun validate() {
        Extras::class.memberProperties.fold(true) { pass, property ->
            val activityProperty = ReaderActivity::class.memberProperties.find { it.name == property.name } ?: return@fold pass.apply { Log.w("unmatched", "Extras.${property.name}") }
            val extra = property.get(Extras)
            try {
            val activityValue = activityProperty.get(this@ReaderActivity)
            if(activityValue == extra) {
                Log.i("pass", property.name)
                pass
            } else {
                Log.e("fail", "Extras.${property.name} = $extra, ReaderActivity.${activityProperty.name} = $activityValue")
                false
            }
            } catch (e: InvocationTargetException) {
                Log.e("invoking", "name = ${property.name}")
                throw e
            }
        }
    }
}

object Extras {
    val charSequenceArrayList: ArrayList<CharSequence> = arrayListOf("charSequence")
    val charSequenceArrayListOptional: ArrayList<CharSequence>? = null
    val intArrayList: ArrayList<Int> = arrayListOf(0, 1, 2)
    val intArrayListOptional: ArrayList<Int>? = null
    val stringArrayList: ArrayList<String> = arrayListOf("string")
    val stringArrayListOptional: ArrayList<String>? = null
    val parcelableArrayList: ArrayList<Box> = arrayListOf(Box("box content"))
    val parcelableArrayListOptional: ArrayList<Box>? = null
    val boolean: Boolean = false
    val booleanArray: BooleanArray = booleanArrayOf(false)
    val booleanArrayOptional: BooleanArray? = null
    val bundle: Bundle = Bundle().apply { putFloat("float", 1.0F) }
    val bundleOptional: Bundle? = null
    val byte: Byte = 0
    val byteArray: ByteArray = byteArrayOf(0, 1, 2)
    val byteArrayOptional: ByteArray? = null
    val char: Char = 'c'
    val charArray: CharArray = charArrayOf('a', 'b', 'c')
    val charArrayOptional: CharArray? = null
    val charSequence: CharSequence = "charSequence"
    val double: Double = 1.0
    val doubleArray: DoubleArray = doubleArrayOf(0.0, 1.0, 2.0)
    val doubleArrayOptional: DoubleArray? = null
    val float: Float = 1.0F
    val floatArray: FloatArray = floatArrayOf(1.0F)
    val floatArrayOptional: FloatArray? = null
    val int: Int = -1
    val intArray: IntArray = intArrayOf(1, 2, 3)
    val intArrayOptional: IntArray? = null
    val long: Long = -1
    val longArray: LongArray = longArrayOf(1, 2, 3)
    val longArrayOptional: LongArray? = null
    val parcelable: Box = Box("box content")
    val parcelableOptional: Box? = null
    val stringArray: Array<String> = arrayOf("string")
    val stringArrayOptional: Array<String>? = null
    val charSequenceArray: Array<CharSequence> = arrayOf("charSequence")
    val charSequenceArrayOptional: Array<CharSequence>? = null
    val parcelableArray: Array<Box> = arrayOf(Box("boxContent"))
    val parcelableArrayOptional: Array<Box>? = null
    val serializable: Box = Box("serializable")
    val serializableOptional: Box? = null
    val short: Short = -1
    val shortArray: ShortArray = shortArrayOf(1)
    val shortArrayOptional: ShortArray? = null
    val string: String = "string"
    val stringOptional: String? = null

}