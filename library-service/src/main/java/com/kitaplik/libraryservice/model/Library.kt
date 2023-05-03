package com.kitaplik.libraryservice.model

import org.hibernate.annotations.GenericGenerator
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
@Entity
data class Library @JvmOverloads constructor (
    @Id
    @Column(name="library-id")
    @GeneratedValue(generator="UUID")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? ="",

    @ElementCollection
    val userBook: List<String> = ArrayList()


)
