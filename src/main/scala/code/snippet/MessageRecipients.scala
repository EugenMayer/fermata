package code.snippet

import _root_.scala.xml.{NodeSeq, Text}
import _root_.net.liftweb.util._
import _root_.net.liftweb.mapper._
import _root_.net.liftweb.common._
import net.liftweb.http.{S,DispatchSnippet,Paginator,PaginatorSnippet,  
  SortedPaginator,SortedPaginatorSnippet}
import net.liftweb.mapper.view.{SortedMapperPaginatorSnippet,SortedMapperPaginator}
import code.lib._
import code.model.Recipient
import code.model.MessageRecipient
import code.model.Message
import Helpers._
import S.?

class MessageRecipients extends DispatchSnippet {

  override def dispatch = {
    case "all" => all _
    case "top" => top _
    case "paginate" => paginator.paginate _
  }

  val paginator = new SortedMapperPaginatorSnippet(MessageRecipient,MessageRecipient.id, "ID" -> MessageRecipient.id){
    override def itemsPerPage = 20
    _sort = (0,false)
    val rcptid = S.param("rcptId") getOrElse {"0"}
    constantParams = List(By(MessageRecipient.recipient,rcptid.toLong))
    override def prevXml: NodeSeq = Text(?("Prev"))
    override def nextXml: NodeSeq = Text(?("Next"))
    override def firstXml: NodeSeq = Text(?("First"))
    override def lastXml: NodeSeq = Text(?("Last"))
  }

  protected def many(mrs: List[MessageRecipient], xhtml: NodeSeq): NodeSeq = {
    val msgs = mrs.map(_.message.obj.open_!)
    //val msgs = MessageRecipient.findAll(By(MessageRecipient.message, this.id)).map(_.recipient.obj.open_!)
    msgs.flatMap(a => single(a,xhtml))
  }

  // Same as Messages.single
  protected def single(msg: Message, xhtml: NodeSeq): NodeSeq =
    bind("a", xhtml,
      "sender" -> msg.sender,
      "subject" -> msg.subject,
      "date" -> msg.sentDate,
      "linkedsubject" -%> <a href={"/msg/"+msg.primaryKeyField}>{msg.subject}</a>
    )
  
//  protected def single(r: Message, xhtml: NodeSeq): NodeSeq =
//    val msgs = MessageRecipient.findAll(By(MessageRecipient.message, this.id)).map(_.recipient.obj.open_!)
//    bind("a", xhtml,
//         "message" -> <a href={"/msg/" + r.message.get.id}>{r.message.get.subject}</a>
//       )
    
  // Display all entries the paginator returns
  def all(xhtml: NodeSeq): NodeSeq = many(paginator.page,xhtml)

  // Show pagination links
  def paginate(xhtml: NodeSeq) {
    paginator.paginate(xhtml)
  }

  // Show most recent, no pagination offsets
  def top(xhtml: NodeSeq) = {
    val count = S.attr("count", _.toInt) openOr 20
    many(MessageRecipient.findAll(MaxRows(count), OrderBy(MessageRecipient.id, Descending)),xhtml)
  }







//  def list(xhtml: NodeSeq) = {
//    val count = S.attr("count", _.toInt) openOr 20
//    bind("recipients", xhtml,
//         "latest" -> Recipient.findAll(MaxRows(count)).flatMap(
//           r => <li><a href={"/recipient/" + r.primaryKeyField}>{r.addressIndex}</a></li>
//         )
//    )
//  }

  def listMessages(xhtml: NodeSeq) = {
    val rcptid = S.param("rcptId") getOrElse {"0"}
    val messages : List[Message] = Message.getMessagesByRecipient(rcptid.toLong)
    val count = S.attr("count", _.toInt) openOr 100
    bind("recipient", xhtml,
         "messages" -> messages.flatMap(
           m => <li><a href={"/msg/" + m.primaryKeyField}>{m.sender}: {m.subject}</a></li>
         )
    )
  }

  def address(xhtml: NodeSeq) = {
    val rcptid = S.param("rcptId") getOrElse {"0"}
    val recipient = Recipient.findByKey(rcptid.toLong)
    recipient match {
      case Full(r) => Text(r.addressIndex)
      case _ => Nil
    }
  }
}
