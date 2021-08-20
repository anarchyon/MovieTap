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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
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
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )
            contactsCursor?.let { cursor ->
                for (i in 0..cursor.count) {
                    if (cursor.moveToPosition(i)) {
                        val name =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        var phoneNumber: String? = null
                        val id =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                        val hasPhone =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                        if (hasPhone.toInt() > 0) {
                            val phoneCursor: Cursor? = contentResolver.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                arrayOf(id), null
                            )
                            phoneCursor?.let { pCursor ->
                                if (pCursor.moveToFirst()) {
                                    phoneNumber = pCursor.getString(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                }
                            }
                            phoneCursor?.close()
                        }
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
        val recyclerView = binding.contactsLayout
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = ContactAdapter(contactsList)
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}