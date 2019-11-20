package de.fuchsch.email.util

import javax.mail.BodyPart
import javax.mail.internet.MimeMultipart

fun MimeMultipart.iterable(): Iterable<BodyPart> =
    Iterable { MimeMultiPartIterator(this) }

class MimeMultiPartIterator(private val multipart: MimeMultipart): Iterator<BodyPart> {

    private var index: Int = 0

    override fun hasNext(): Boolean = index < multipart.count

    override fun next(): BodyPart = multipart.getBodyPart(index++)

}