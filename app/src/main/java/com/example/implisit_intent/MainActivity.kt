package com.example.implisit_intent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.CalendarContract
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    // =========================================================
    // DEKLARASI KOMPONEN
    // =========================================================

    private lateinit var btnKirimPesan: Button
    private lateinit var btnSetAlarm: Button
    private lateinit var btnSetTimer: Button
    private lateinit var btnOpenURL: Button
    private lateinit var btnSetEvent: Button
    private lateinit var btnGetPhoto: Button
    private lateinit var btnBukaMaps: Button

    private lateinit var etURL: EditText
    private lateinit var ivHasil: ImageView


    // =========================================================
    // CAMERA
    // =========================================================

    private val cameraLauncher =
        registerForActivityResult(
            ActivityResultContracts.TakePicturePreview()
        ) { bitmap: Bitmap? ->

            if (bitmap != null) {

                ivHasil.setImageBitmap(bitmap)

                Toast.makeText(
                    this,
                    "Foto berhasil diambil",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                Toast.makeText(
                    this,
                    "Foto tidak diambil",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    // =========================================================
    // ON CREATE
    // =========================================================

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_main)


        // =====================================================
        // EDGE TO EDGE
        // =====================================================

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v, insets ->

            val systemBars =
                insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                )

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }


        // =====================================================
        // INISIALISASI KOMPONEN
        // =====================================================

        btnKirimPesan =
            findViewById(R.id.btnKirimPesan)

        btnSetAlarm =
            findViewById(R.id.btnSetAlarm)

        btnSetTimer =
            findViewById(R.id.btnSetTimer)

        btnOpenURL =
            findViewById(R.id.btnOpenURL)

        btnSetEvent =
            findViewById(R.id.btnSetEvent)

        btnGetPhoto =
            findViewById(R.id.btnGetPhoto)

        btnBukaMaps =
            findViewById(R.id.btnBukaMaps)

        etURL =
            findViewById(R.id.etURL)

        ivHasil =
            findViewById(R.id.ivHasil)


        // =========================================================
// 1. MENGIRIM PESAN / SMS
// =========================================================

        btnKirimPesan.setOnClickListener {

            val _sendIntent = Intent().apply {

                action = Intent.ACTION_SEND

                putExtra(
                    "address",
                    "0811234"
                )

                putExtra(
                    "sms_body",
                    "ISI SMS"
                )

                type = "text/plain"
            }


            if (
                _sendIntent.resolveActivity(
                    packageManager
                ) != null
            ) {

                startActivity(_sendIntent)
            }
        }


        // =========================================================
// 2. SET ALARM
// =========================================================

        btnSetAlarm.setOnClickListener {

            val _alarmIntent =
                Intent(
                    AlarmClock.ACTION_SET_ALARM
                ).apply {

                    putExtra(
                        AlarmClock.EXTRA_MESSAGE,
                        "COBA ALARM"
                    )

                    putExtra(
                        AlarmClock.EXTRA_HOUR,
                        20
                    )

                    putExtra(
                        AlarmClock.EXTRA_MINUTES,
                        15
                    )
                }

            startActivity(_alarmIntent)
        }


        // =========================================================
// 3. SET TIMER
// =========================================================

        btnSetTimer.setOnClickListener {

            val _timerIntent =
                Intent(
                    AlarmClock.ACTION_SET_TIMER
                ).apply {

                    putExtra(
                        AlarmClock.EXTRA_MESSAGE,
                        "COBA ALARM"
                    )

                    putExtra(
                        AlarmClock.EXTRA_LENGTH,
                        20
                    )

                    putExtra(
                        AlarmClock.EXTRA_SKIP_UI,
                        true
                    )
                }

            startActivity(_timerIntent)
        }


        // =========================================================
        // 4. MEMBUKA WEBSITE
        // =========================================================

        btnOpenURL.setOnClickListener {

            var url =
                etURL.text.toString().trim()


            // Jika URL kosong
            if (url.isEmpty()) {

                Toast.makeText(
                    this,
                    "Masukkan URL terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }


            // Tambahkan https:// jika belum ada
            if (
                !url.startsWith("http://") &&
                !url.startsWith("https://")
            ) {

                url = "https://$url"
            }


            val webIntent =
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url)
                )


            if (
                webIntent.resolveActivity(
                    packageManager
                ) != null
            ) {

                startActivity(webIntent)

            } else {

                Toast.makeText(
                    this,
                    "Browser tidak ditemukan",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        // =========================================================
        // 5. MENAMBAHKAN EVENT CALENDAR
        // =========================================================

        btnSetEvent.setOnClickListener {

            val calendar =
                Calendar.getInstance()


            // =====================================================
            // TANGGAL SEKARANG
            // =====================================================

            val year =
                calendar.get(Calendar.YEAR)

            val month =
                calendar.get(Calendar.MONTH)

            val day =
                calendar.get(Calendar.DAY_OF_MONTH)


            // =====================================================
            // WAKTU SEKARANG
            // =====================================================

            val hour =
                calendar.get(Calendar.HOUR_OF_DAY)

            val minute =
                calendar.get(Calendar.MINUTE)


            // =====================================================
            // DATE PICKER
            // =====================================================

            val datePickerDialog =
                DatePickerDialog(
                    this,

                    { _, selectedYear,
                      selectedMonth,
                      selectedDay ->


                        // =========================================
                        // TIME PICKER
                        // =========================================

                        val timePickerDialog =
                            TimePickerDialog(
                                this,

                                { _, selectedHour,
                                  selectedMinute ->


                                    // =================================
                                    // WAKTU MULAI
                                    // =================================

                                    val startTime =
                                        Calendar.getInstance()

                                    startTime.set(
                                        selectedYear,
                                        selectedMonth,
                                        selectedDay,
                                        selectedHour,
                                        selectedMinute
                                    )


                                    // =================================
                                    // WAKTU SELESAI
                                    // 1 JAM SETELAH MULAI
                                    // =================================

                                    val endTime =
                                        startTime.clone()
                                                as Calendar

                                    endTime.add(
                                        Calendar.HOUR_OF_DAY,
                                        1
                                    )


                                    // =================================
                                    // EVENT INTENT
                                    // =================================

                                    val eventIntent =
                                        Intent(
                                            Intent.ACTION_INSERT
                                        ).apply {

                                            data =
                                                CalendarContract
                                                    .Events
                                                    .CONTENT_URI


                                            putExtra(
                                                CalendarContract.Events.TITLE,
                                                "Event Intent Implisit"
                                            )


                                            putExtra(
                                                CalendarContract.Events.EVENT_LOCATION,
                                                "Universitas Kristen Petra"
                                            )


                                            putExtra(
                                                CalendarContract.Events.DESCRIPTION,
                                                "Event dibuat menggunakan Intent Implisit"
                                            )


                                            putExtra(
                                                CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                                startTime.timeInMillis
                                            )


                                            putExtra(
                                                CalendarContract.EXTRA_EVENT_END_TIME,
                                                endTime.timeInMillis
                                            )
                                        }


                                    // =================================
                                    // CEK CALENDAR
                                    // =================================

                                    if (
                                        eventIntent.resolveActivity(
                                            packageManager
                                        ) != null
                                    ) {

                                        startActivity(
                                            eventIntent
                                        )

                                    } else {

                                        Toast.makeText(
                                            this,
                                            "Aplikasi Calendar tidak ditemukan",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                },

                                hour,
                                minute,
                                true
                            )


                        timePickerDialog.show()
                    },

                    year,
                    month,
                    day
                )


            datePickerDialog.show()
        }


        // =========================================================
        // 6. CAMERA
        // =========================================================

        btnGetPhoto.setOnClickListener {

            cameraLauncher.launch(null)
        }


        // =========================================================
        // 7. GOOGLE MAPS
        // =========================================================

        btnBukaMaps.setOnClickListener {

            val latitude =
                "-7.24611"

            val longitude =
                "112.73750"

            val labelTempat =
                "Tugu Pahlawan"


            // =====================================================
            // URI GOOGLE MAPS
            // =====================================================

            val mapIntentUri =
                Uri.parse(
                    "geo:$latitude,$longitude?q=$latitude,$longitude($labelTempat)"
                )


            // =====================================================
            // MAPS INTENT
            // =====================================================

            val mapIntent =
                Intent(
                    Intent.ACTION_VIEW,
                    mapIntentUri
                ).apply {

                    setPackage(
                        "com.google.android.apps.maps"
                    )
                }


            // =====================================================
            // CEK GOOGLE MAPS
            // =====================================================

            if (
                mapIntent.resolveActivity(
                    packageManager
                ) != null
            ) {

                startActivity(mapIntent)

            } else {

                // =================================================
                // JIKA GOOGLE MAPS TIDAK ADA,
                // BUKA GOOGLE MAPS MELALUI BROWSER
                // =================================================

                val webUri =
                    Uri.parse(
                        "https://www.google.com/maps/search/?api=1&query=$latitude,$longitude"
                    )


                val webIntent =
                    Intent(
                        Intent.ACTION_VIEW,
                        webUri
                    )


                if (
                    webIntent.resolveActivity(
                        packageManager
                    ) != null
                ) {

                    startActivity(webIntent)

                } else {

                    Toast.makeText(
                        this,
                        "Browser tidak tersedia",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}