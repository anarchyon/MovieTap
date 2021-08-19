package project.paveltoy.movietap.ui.contacts

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import project.paveltoy.movietap.databinding.FragmentContactsBinding

private const val PERMISSION_REQUEST_CONTACTS = 22

class ContactsShow : Fragment() {
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    private var contactsList = arrayListOf<ArrayList<String?>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkContactsPermission()
    }

    private fun checkContactsPermission() {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_CONTACTS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    getContacts()
                }
                else -> {
                    requestContactsPermission()
                }
            }
        }
    }

    private fun requestContactsPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.READ_CONTACTS),
            PERMISSION_REQUEST_CONTACTS
        )
    }

    private fun getContacts() {
        context?.let {
            val contentResolver: ContentResolver = it.contentResolver
            val contactsCursor: Cursor? = contentResolver.query(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                null, null, null,
                ContactsContract.PhoneLookup.DISPLAY_NAME + " ASC"
            )
            contactsCursor?.let { cursor ->
                for (i in 0..cursor.count) {
                    if (cursor.moveToPosition(i)) {
                        val name = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME))
                        val phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.NORMALIZED_NUMBER))
                        addData(name, phoneNumber)
                    }
                }
            }
            contactsCursor?.close()
        }
        initRecyclerView()
    }

    private fun addData(name: String, phoneNumber: String?) {
        val contact = arrayListOf(name, phoneNumber)
        contactsList.add(contact)
    }

    private fun initRecyclerView() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}