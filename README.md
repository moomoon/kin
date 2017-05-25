# kin
Pass values between activities in a type-safe manner.

# Usage
```kotlin
class A: Activity() {
  ...
  startActivity<B> {
    B::value willBe 10
  }
  ...
}

class B: Activity() {
  val value: Int by Kin.Int(defaultValue = -1)
  
  fun magicFunction() = Toast.makeText(this, "value = $value", Toast.LENGTH_SHORT).show()
}
```


## Installation
Available via [JitPack](https://jitpack.io/).

### Step 1.
Add it in your root build.gradle at the end of repositories:
```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
### Step 2. 
Add the dependency
```groovy
	dependencies {
	        compile 'com.github.moomoon:kin:0.1'
	}
```
