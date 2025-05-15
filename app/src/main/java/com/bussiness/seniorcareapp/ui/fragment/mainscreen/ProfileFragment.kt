package com.bussiness.seniorcareapp.ui.fragment.mainscreen

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bussiness.seniorcareapp.databinding.FragmentProfileBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    private var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Register permission request callback
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val granted = permissions.all { it.value }
            if (granted) {
                showImagePickerDialog()
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

        // Camera result handler
        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                photoUri?.let { binding.profileImage.setImageURI(it) }
            }
        }

        // Gallery result handler
        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { binding.profileImage.setImageURI(it) }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setEditMode(edtEmail, false)
            setEditMode(edtPhone, false)
            setEditMode(edtName, false)
            setEditMode(edtLocation, false)
        }

        setupClickListeners()
    }

    @SuppressLint("SetTextI18n")
    private fun setupClickListeners() = with(binding) {
        editProfile.setOnClickListener {
            toggleEditMode(true)
            editProfile.visibility = View.GONE
            title.text = "Edit Profile"
        }

        saveChanges.setOnClickListener {
            toggleEditMode(false)
            editProfile.visibility = View.VISIBLE
            title.text = "Profile"
        }

        editCameraBtn.setOnClickListener {
            checkPermissionsAndOpenPicker()
        }

        ivMenu.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun toggleEditMode(enable: Boolean) = with(binding) {
        val visibility = if (enable) View.VISIBLE else View.GONE

        editCameraBtn.visibility = visibility
        saveChanges.visibility = visibility
        emailVerify.visibility = visibility
        phoneVerify.visibility = visibility
        editIcon.visibility = visibility
        locationEditIcon.visibility = visibility

        setEditMode(edtEmail, enable)
        setEditMode(edtPhone, enable)
        setEditMode(edtName, enable)
        setEditMode(edtLocation, enable)
    }

    private fun setEditMode(editText: EditText, enable: Boolean) {
        editText.isEnabled = enable
        editText.isFocusable = enable
        editText.isFocusableInTouchMode = enable
    }


    private fun checkPermissionsAndOpenPicker() {
        val requiredPermissions = mutableListOf<String>().apply {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                add(Manifest.permission.CAMERA)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                    add(Manifest.permission.READ_MEDIA_IMAGES)
                }
            } else {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    add(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }

        if (requiredPermissions.isNotEmpty()) {
            requestPermissionLauncher.launch(requiredPermissions.toTypedArray())
        } else {
            showImagePickerDialog()
        }
    }

    private fun showImagePickerDialog() {
        val options = arrayOf("Camera", "Gallery")
        AlertDialog.Builder(requireContext())
            .setTitle("Select Image Source")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }
            .show()
    }

    private fun openCamera() {
        val photoFile = createImageFile() ?: run {
            Toast.makeText(requireContext(), "Could not create image file", Toast.LENGTH_SHORT).show()
            return
        }

        photoUri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            photoFile
        )

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        }

        cameraLauncher.launch(intent)
    }

    private fun createImageFile(): File? {
        return try {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
        }
        galleryLauncher.launch(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
