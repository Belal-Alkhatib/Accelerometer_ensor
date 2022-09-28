package com.b.alkhatib.accelerometersensor

import android.content.Context
import android.hardware.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.b.alkhatib.accelerometersensor.databinding.ActivityMainBinding
import java.text.DecimalFormat

/**
 1* لتحديد اجهزة الاستشعارات نعمل ريفيرنس من السينسور سيرفيس/ السينسور مانيجر استخدمه للوصول الى نوع السينسور المراد وللاستماع لسينسور معين ولاحدد معدلات الوصول للبيانات من المجسات
 2* بهدف جلب جميع المجسات التي في الجهاز عبرgetSensorList والتي ترجع list من السينسورز
  3* لاني احتاج للتنصت على السينسور أحتاج لمثود تعمل هذا -> منخلال عمل امبلمنت لكلاسSensorEventListener ثم استدعاء ال2مثود "عند حدوث اي حدث سيتم استدعاءهما"
 4* يجب عمل تسجيل للتطبيق لتستمع للسينسور والا لن يعمل وذلك من خلال دالة registerListener التي تاخذ
  * */
class MainActivity : AppCompatActivity(), SensorEventListener {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var sensorManager: SensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager // 1
        val mySensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL) //2
        /* //لطباعة كل السينسور الموجودة بالجهاز
        for (sensor in mySensors) {
            println("Sensor Name: "+sensor.name)
        }*/

        // لفحص هل اذا السينسور موجود في الجهاز ام لا
        var s = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if(s != null){
            println("Sensor is found ☺");
        }else{
            println("Sensor is not found!!!");
        }

        sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL) // 4* SENSOR_DELAY_NORMAL: تمثل قيمة التأخيراو قيمة المدة الزمنية بين أول قيمة والثانية


    }

    //3
    override fun onSensorChanged(event: SensorEvent?) { //تعطي اي قيمة جديدة للمجس عند حدوث جدث + تعطي من هو السينسور الذي حدث تغيير على حالته
      // p0:SensorEvent من خلاله اتي ب القيمة البيانات مثل استهلاكه للطاقةوهعلى وادنلى قيمة ..الخ

        if (event != null) {
            if(event.sensor.type == Sensor.TYPE_ACCELEROMETER){
                var df = DecimalFormat("0.0")
                // v:  و نتعامل معه بالاندكسarrتمثل جميع قيم الوتبوت والقيم الخاصة بسينسور ومرجعها عبارة عن
                var x = event.values[0]
                var y = event.values[1]
                var z = event.values[2]

                // get device dimensions
                val displayMetrics = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(displayMetrics)

                var width = displayMetrics.widthPixels //1080
                var height = displayMetrics.heightPixels

                var newWidth = binding.imageView.width
                var newHeight = binding.imageView.height

                binding.tvTest.setText("X: "+df.format(x)+ "newWidth: $width \nY: "+df.format(y)+"newHeight: $newHeight\nZ: "+df.format(z))

                if(newWidth*newHeight < width*height){
                    binding.imageView.setX(binding.imageView.x - (x*2))
                    binding.imageView.setY(binding.imageView.y + (y*3))
                }else{
                    //binding.imageView.setX(binding.imageView.x)
                   // binding.imageView.setY(binding.imageView.y)
                }
                
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) { // توضح التغييرات التي تحصل على دقة المجس. ويتم تمثيل دقة المجس من خلال ثوابت يعبر عنها من خلال ثوايت(low,midiam,high)

    }
}