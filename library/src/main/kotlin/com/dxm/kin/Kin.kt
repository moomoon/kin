package com.dxm.kin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.RequiresApi
import android.util.Log
import java.io.Serializable
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1

/**
 * Created by ants on 17/05/2017.
 */

inline fun Illegal(reason: String): Nothing = throw IllegalArgumentException(reason)


inline fun <reified T : Activity> Activity.startActivity(setup: Kin<T>.() -> Unit) = startActivity(Kin(this, T::class.java).apply { setup() })
inline fun <reified T : Activity> Activity.startActivityForResult(requestCode: Int, setup: Kin<T>.() -> Unit) = startActivityForResult(Kin(this, T::class.java).apply { setup() }, requestCode)
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
inline fun <reified T : Activity> Activity.startActivity(options: Bundle, setup: Kin<T>.() -> Unit) = startActivity(Kin(this, T::class.java).apply { setup() }, options)
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
inline fun <reified T : Activity> Activity.startActivityForResult(requestCode: Int, options: Bundle, setup: Kin<T>.() -> Unit) = startActivityForResult(Kin(this, T::class.java).apply { setup() }, requestCode, options)


class Kin<T : Activity>(context: Context, clazz: Class<T>) : Intent(context, clazz) {

    @JvmName("charSequenceWillBe")
    infix fun KProperty1<T, ArrayList<CharSequence>?>.willBe(value: ArrayList<CharSequence>?) = putCharSequenceArrayListExtra(name, value)

    infix fun KProperty1<T, Boolean>.willBe(value: Boolean) = putExtra(name, value)
    infix fun KProperty1<T, BooleanArray?>.willBe(value: BooleanArray?) = putExtra(name, value)
    infix fun KProperty1<T, Bundle?>.willBe(value: Bundle?) = putExtra(name, value)
    infix fun KProperty1<T, Byte>.willBe(value: Byte) = putExtra(name, value)
    infix fun KProperty1<T, ByteArray?>.willBe(value: ByteArray?) = putExtra(name, value)
    infix fun KProperty1<T, Char>.willBe(value: Char) = putExtra(name, value)
    infix fun KProperty1<T, CharArray?>.willBe(value: CharArray?) = putExtra(name, value)
    infix fun KProperty1<T, CharSequence?>.willBe(value: CharSequence?) = putExtra(name, value)
    infix fun KProperty1<T, Array<CharSequence>?>.willBe(value: Array<CharSequence>?) = putExtra(name, value)
    infix fun KProperty1<T, Double>.willBe(value: Double) = putExtra(name, value)
    infix fun KProperty1<T, DoubleArray?>.willBe(value: DoubleArray?) = putExtra(name, value)
    infix fun KProperty1<T, Float>.willBe(value: Float) = putExtra(name, value)
    infix fun KProperty1<T, FloatArray?>.willBe(value: FloatArray?) = putExtra(name, value)
    infix fun KProperty1<T, Int>.willBe(value: Int) = putExtra(name, value)
    infix fun KProperty1<T, IntArray?>.willBe(value: IntArray?) = putExtra(name, value)
    infix fun KProperty1<T, Long>.willBe(value: Long) = putExtra(name, value)
    infix fun KProperty1<T, LongArray?>.willBe(value: LongArray?) = putExtra(name, value)
    infix fun <R : Parcelable> KProperty1<T, Array<R>?>.willBe(value: Array<R>?) = putExtra(name, value)
    infix fun <R : Parcelable> KProperty1<T, R?>.willBe(value: R?) = putExtra(name, value)
    infix fun <R : Serializable> KProperty1<T, R?>.willBe(value: R?) = putExtra(name, value)
    infix fun KProperty1<T, Short>.willBe(value: Short) = putExtra(name, value)
    infix fun KProperty1<T, ShortArray?>.willBe(value: ShortArray?) = putExtra(name, value)
    infix fun KProperty1<T, String?>.willBe(value: String?) = putExtra(name, value)
    infix fun KProperty1<T, Array<String>?>.willBe(value: Array<String>?) = putExtra(name, value)
    @JvmName("integerArrayListWillBe")
    infix fun KProperty1<T, ArrayList<Int>?>.willBe(value: ArrayList<Int>?) {
        Log.e("putArrayList<Int>", "name = $name, value = $value")
        putIntegerArrayListExtra(name, value)
    }

    @JvmName("parcelableArrayListWillBe")
    infix fun <R : Parcelable> KProperty1<T, ArrayList<R>?>.willBe(value: ArrayList<R>?) {
        Log.e("putArrayList", "name = $name, value = $value")
        putParcelableArrayListExtra(name, value)
    }

    @JvmName("stringArrayListWillBe")
    infix fun KProperty1<T, ArrayList<String>?>.willBe(value: ArrayList<String>?) {
        Log.e("putArrayList<String>", "name = $name, value = $value")
        putStringArrayListExtra(name, value)
    }

    companion object {
        inline fun <T: Any> (Intent.(String) -> T).optionalReader() = let(::OptionalLazyIntentReader)
        fun <T> (Intent.(String, T) -> T).reader(defaultValue: T): ReadOnlyProperty<Activity, T> = LazyIntentReader { intent, name -> intent.this(name, defaultValue) }
        inline fun <reified T> ArrayList(): OptionalLazyIntentReader<ArrayList<T>> = when (T::class.java) {
            CharSequence::class.java -> Intent::getCharSequenceArrayListExtra.optionalReader()
            Int::class.java, Integer::class.java -> Intent::getIntegerArrayListExtra.optionalReader()
            String::class.java -> Intent::getStringArrayListExtra.optionalReader()
            else -> if (Parcelable::class.java.isAssignableFrom(T::class.java))
                OptionalLazyIntentReader { intent, name -> intent.apply { setExtrasClassLoader(T::class.java.classLoader) }.getParcelableArrayListExtra<Parcelable>(name) }
            else Illegal("ArrayList<${T::class.java}> is not supported.")
        } as OptionalLazyIntentReader<ArrayList<T>>

        fun Boolean(defaultValue: Boolean): ReadOnlyProperty<Activity, Boolean> = Intent::getBooleanExtra.reader(defaultValue)
        fun BooleanArray(): OptionalLazyIntentReader<BooleanArray> = Intent::getBooleanArrayExtra.optionalReader()
        fun Bundle(): OptionalLazyIntentReader<Bundle> = Intent::getBundleExtra.optionalReader()
        fun Byte(defaultValue: Byte): ReadOnlyProperty<Activity, Byte> = Intent::getByteExtra.reader(defaultValue)
        fun ByteArray(): OptionalLazyIntentReader<ByteArray> = Intent::getByteArrayExtra.optionalReader()
        fun Char(defaultValue: Char): ReadOnlyProperty<Activity, Char> = Intent::getCharExtra.reader(defaultValue)
        fun CharArray(): OptionalLazyIntentReader<CharArray> = Intent::getCharArrayExtra.optionalReader()
        fun CharSequence(): OptionalLazyIntentReader<CharSequence> = Intent::getCharSequenceExtra.optionalReader()
        fun Double(defaultValue: Double): ReadOnlyProperty<Activity, Double> = Intent::getDoubleExtra.reader(defaultValue)
        fun DoubleArray(): OptionalLazyIntentReader<DoubleArray> = Intent::getDoubleArrayExtra.optionalReader()
        fun Float(defautValue: Float): ReadOnlyProperty<Activity, Float> = Intent::getFloatExtra.reader(defautValue)
        fun FloatArray(): OptionalLazyIntentReader<FloatArray> = Intent::getFloatArrayExtra.optionalReader()
        fun Int(defaultValue: Int): ReadOnlyProperty<Activity, Int> = Intent::getIntExtra.reader(defaultValue)
        fun IntArray(): OptionalLazyIntentReader<IntArray> = Intent::getIntArrayExtra.optionalReader()
        fun Long(defaultValue: Long): ReadOnlyProperty<Activity, Long> = Intent::getLongExtra.reader(defaultValue)
        fun LongArray(): OptionalLazyIntentReader<LongArray> = Intent::getLongArrayExtra.optionalReader()
        inline fun <reified T : Parcelable> Parcelable(): OptionalLazyIntentReader<T> = OptionalLazyIntentReader { intent, name -> intent.apply { setExtrasClassLoader(T::class.java.classLoader) }.getParcelableExtra<T>(name) }
        inline fun <reified T: Any> Array(): OptionalLazyIntentReader<Array<T>> = when (T::class.java) {
            String::class.java -> Intent::getStringArrayExtra.optionalReader()
            CharSequence::class.java -> Intent::getCharSequenceArrayExtra.optionalReader()
            else -> if (Parcelable::class.java.isAssignableFrom(T::class.java))
                OptionalLazyIntentReader { intent, name -> intent.apply { setExtrasClassLoader(T::class.java.classLoader) }.getParcelableArrayExtra(name)?.mapToArray { it as T }  }
            else Illegal("Array<${T::class.java}> is not supported.")
        } as OptionalLazyIntentReader<Array<T>>
        inline fun <reified T : Serializable> Serializable(): OptionalLazyIntentReader<T> = OptionalLazyIntentReader { intent, name -> intent.getSerializableExtra(name) } as OptionalLazyIntentReader<T>
        fun Short(defaultValue: Short): ReadOnlyProperty<Activity, Short> = Intent::getShortExtra.reader(defaultValue)
        fun ShortArray(): OptionalLazyIntentReader<ShortArray> = Intent::getShortArrayExtra.optionalReader()
        fun String(): OptionalLazyIntentReader<String> = Intent::getStringExtra.optionalReader()
    }
}
fun <T> T.print(tag: String) = also { Log.e("DXM", "$tag = $this") }
inline fun <T, reified U> Array<T>.mapToArray(transform: (T) -> U) = Array(size) { transform(get(it)) }

private object UNINITIALIZED_VALUE
open class LazyIntentReader<out T>(initializer: (intent: Intent, name: String) -> T) : ReadOnlyProperty<Activity, T> {
    private var initializer: ((intent: Intent, name: String) -> T)? = initializer
    @Volatile private var _value: Any? = UNINITIALIZED_VALUE
    private val lock = this

    override fun getValue(thisRef: Activity, property: KProperty<*>): T {
        val _v1 = _value
        if (_v1 !== UNINITIALIZED_VALUE) {
            @Suppress("UNCHECKED_CAST")
            return _v1 as T
        }

        return synchronized(lock) {
            val _v2 = _value
            if (_v2 !== UNINITIALIZED_VALUE) {
                @Suppress("UNCHECKED_CAST") (_v2 as T)
            } else {
                val intent = thisRef.intent
                val typedValue = initializer!!(intent, property.name)
                _value = typedValue
                initializer = null
                typedValue
            }
        }
    }
}
class OptionalLazyIntentReader<out T>(initializer: (intent: Intent, name: String) -> T): LazyIntentReader<T>(initializer) {
    val optional: ReadOnlyProperty<Activity, T?> = this
}