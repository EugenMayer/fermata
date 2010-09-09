package code.model

import _root_.net.liftweb.mapper._
import _root_.net.liftweb.util._
import _root_.net.liftweb.common._

import com.sun.mail.smtp.SMTPMessage

import java.io.{IOException, InputStream, ByteArrayInputStream}


class Message extends LongKeyedMapper[Message] with IdPK {
  def getSingleton = Message
  object msgBody extends MappedBinary(this)
  object subject extends MappedText(this)
  object sender extends MappedEmail(this, 256)
  object sentDate extends MappedDateTime(this)
  object messageId extends MappedString(this, 256)
  object textContent extends MappedText(this)
  object lazy_recipients extends HasManyThrough(this, Recipient,
    MessageRecipient, MessageRecipient.recipient, MessageRecipient.message)

  def recipients = MessageRecipient.findAll(By(MessageRecipient.message, this.id)).map(_.recipient.obj.open_!)

  def recipientsPrintable() : String = {
    val rcpts = recipients.map({x => x.addressIndex.get}).reverse
    rcpts mkString(", ")
  }

  def getHeaders() : String = {
    val msg = new SMTPMessage(null, new ByteArrayInputStream(msgBody))
    val headers = msg.getAllHeaderLines()
    val headertext = new StringBuilder()
    while(headers.hasMoreElements()) {
      headertext.append(headers.nextElement()).append("\n")
    }
    headertext.toString()
  }

}

object Message extends Message with LongKeyedMetaMapper[Message] {
  override def dbTableName = "messages"
  override def fieldOrder = List(sender,subject,sentDate,msgBody)

  def getMessageById(id : Long) : Box[Message] = {
    val msg : List[Message] = Message.findAll(By(Message.primaryKeyField, id))
    val msgbox = msg match {
      case Nil => Empty
      case m :: _ => Full(m)
    }
    msgbox
  }

  def getLatestMessage() : Box[Message] = {
    val msg : List[Message] = Message.findAll(OrderBy(Message.primaryKeyField, Descending), MaxRows(1))
    val msgbox = msg match {
      case Nil => Empty
      case m :: _ => Full(m)
    }
    msgbox
  }

  def getMessagesByRecipient(id : Long) : List[Message] = {
    MessageRecipient.findAll(By(MessageRecipient.recipient, id)).map(_.message.obj.open_!)
  }

}
