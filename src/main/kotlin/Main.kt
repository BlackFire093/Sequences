package ru.netology

import ru.netology.Service.addChat
import ru.netology.Service.addMessageChat
import java.io.IOException
import java.time.LocalDateTime

//класс общих данных чата
data class Chat(val id: Int, val idUser: Int, val name: String, var messages: MutableList<Message>)

//класс сообщения чата, параметр me - кто добавляет сообщение, true - от себя, false - пользователь
data class Message(var id: Int, val me: Boolean, val message: String, val dateTime: LocalDateTime, var view: Boolean)

//исключение
class PostNotFoundException(message: String): RuntimeException(message)

fun main() {
    //добавление чатов
    var chat=Chat(0, 0, "Михаил", mutableListOf())
    chat.addChat(Message(0, true, "Привет!", LocalDateTime.of(2024, 10, 10, 8, 0), true))
    var message = Message(0, false, "Приветствую", LocalDateTime.of(2024, 10, 10, 9, 15), false)
    message.addMessageChat(1)

    chat=Chat(0, 0, "Дмитрий", mutableListOf())
    message = Message(0, true, "Как дела?", LocalDateTime.of(2024, 10, 10, 7, 35), true)
    chat.addChat(message)
    message = Message(0, false, "Все хорошо, спасибо, как у вас дела?", LocalDateTime.of(2024, 10, 10, 8, 43), true)
    message.addMessageChat(2)
    message = Message(0, true, "И у меня все отлично, спасибо.", LocalDateTime.of(2024, 10, 10, 8, 47), true)
    message.addMessageChat(2)

    chat=Chat(0, 0, "Сергей", mutableListOf())
    message = Message(0, true, "Здравствуйте, как насчет позаниматься?", LocalDateTime.of(2024, 10, 11, 7, 0), true)
    chat.addChat(message)
    message = Message(0, false, "Конечно можно и даже нужно.", LocalDateTime.of(2024, 10, 11, 7, 3), true)
    message.addMessageChat(3)
    message = Message(0, true, "Тогда в 10:00 начнем, я думаю.", LocalDateTime.of(2024, 10, 11, 7, 11), true)
    message.addMessageChat(3)

    chat=Chat(0, 0, "Алексей", mutableListOf())
    message = Message(0, true, "Здравствуйте, когда можно посмотреть товар?", LocalDateTime.of(2024, 10, 12, 10, 0), true)
    chat.addChat(message)
    message = Message(0, false, "В любое время, когда вас устроит?.", LocalDateTime.of(2024, 10, 12, 10, 10), true)
    message.addMessageChat(4)
    message = Message(0, true, "Думаю в выходные самое подходящее время.", LocalDateTime.of(2024, 10, 12, 10, 21), true)
    message.addMessageChat(4)
    message = Message(0, false, "Хорошо, я буду ждать, только уточните в какой день и время.", LocalDateTime.of(2024, 10, 12, 10, 45), false)
    message.addMessageChat(4)

    chat=Chat(0, 0, "Роман", mutableListOf())
    message = Message(0, true, "Пойдем на рыбалку сегодня?", LocalDateTime.of(2024, 10, 12, 6, 0), true)
    chat.addChat(message)
    message = Message(0, false, "Привет, в какое время и куда?.", LocalDateTime.of(2024, 10, 12, 6, 10), true)
    message.addMessageChat(5)
    message = Message(0, true, "В часиков 10 можно на озеро.", LocalDateTime.of(2024, 10, 12, 6, 21), true)
    message.addMessageChat(5)
    message = Message(0, false, "Хорошо, я соберу все необходимое.", LocalDateTime.of(2024, 10, 12, 6, 45), false)
    message.addMessageChat(5)
    message = Message(0, false, "И еще можно после рыбалки покупаться в озере.", LocalDateTime.of(2024, 10, 12, 7, 5), false)
    message.addMessageChat(5)
    Service.print(Service.chats)

    //удаление чата (всех сообщений)
    Service.deleteChat(1)
    println("Чат после удаления чата")
    Service.print(Service.chats)

    //удаление сообщения в чате
    println("Чат после удаления сообщения в чате")
    Service.deleteMessageChat(10)
    Service.print(Service.chats)

    //получение списка сообщений определенного чата и определенного количества сообщений
    println("Получение списка сообщений определенного чата и определенного количества сообщений")
    val messages = Service.getMessagesChat(4, 5)
    if (messages != null) {
        Service.print(messages.toMutableList())
    }else println("нет сообщений")

    //вывод последних сообщений чатов
    println("Последние сообщения чатов")
    Service.printLastMessages()

    //получение списка чатов
    println()
    println("Списки чатов")
    Service.printChats()

    //определение количества непрочитанных чатов
    println()
    println("Не прочитанных чатов: "+Service.getUnreadChatsCount())
}

//объект по обслуживанию чатов
object Service{
    var chats = mutableListOf<Chat>()//коллекция чатов
    private var lastIdChat = 0//счетчик кодов чатов
    private var lastIdMessage = 0//счетчик кодов сообщений
    private var lastIdUser = 0//счетчик пользователей

    //добавление чата, extension функция
    fun Chat.addChat(message: Message){
        lastIdMessage++
        message.id = lastIdMessage
        this.messages += message
        chats += this.copy(id = ++lastIdChat, idUser = ++lastIdUser)
    }

    //добавление сообщения в чат, если id_user=0, то сообщение от себя
    //extension функция
    fun Message.addMessageChat(id_chat: Int): Boolean{
        chats.onEach {//применение lambda в массиве
            if (id_chat == it.id) {//если чат найден
                it.messages += this.copy(id = ++lastIdMessage)
                return true
            }
        }
        //если чат не найден, то создаем новый чат
        var message=Message(0, true, "Привет!", LocalDateTime.now(), true)
        var chat=Chat(0, lastIdUser, "Новый чат", mutableListOf(message))
        chat.addChat(message)
        return true
    }

    //удаление чата (всех сообщений) при помощи lambda выражения
    fun deleteChat(id_chat: Int): Boolean {
        var chat = chats.firstNotNullOf{item -> item.takeIf { it.id == id_chat } }
        if(chat.id == id_chat){
            chat.messages.clear()
            return true
        }
        return false
    }

    //удаление сообщения в чате при помощи lambda выражения
    fun deleteMessageChat(id_message: Int): Boolean {
        if(chats.firstNotNullOf{item -> item.messages.removeIf{it.id == id_message}}) return true
        return false
    }

    //получение списка сообщений из чата, на вход подается id собеседника и количество сообщений в чате для просмотра
    //применяются lambda выражения
    fun getMessagesChat(id_user: Int, amount: Int): List<Message>? {
        //получаем данные чата по id чата
        val chat = chats.firstNotNullOfOrNull { item -> item.takeIf { it.idUser == id_user } }
        //если чат существует с таким id
        if (chat != null) {
            //проверяем что в чате есть сообщения
            if (chat.messages.isEmpty() == false) {
                var new_amount = amount
                //если входной параметр по количеству сообщений больше чем в нем есть
                if (amount > chat.messages.size) new_amount = chat.messages.size
                //получаем только сообщения до определенного количества
                val messages = chat.messages.slice(0..new_amount - 1)
                //делаем сообщения прочитанными
                messages.filter { it.view == false }.forEach { it.view = true }
                return messages
            }
            return null
        }
        return null
    }

    //получение списка всех сообщений из чата, на вход подается id собеседника
    //применяются lambda выражения, используем Sequence
    fun getAllMessagesChat(id_user: Int): List<Message>? {
        //получаем данные чата по id чата
        val chat = chats.firstNotNullOfOrNull { item -> item.takeIf { it.idUser == id_user } }
        //если чат существует с таким id
        if (chat != null) {
            //проверяем что в чате есть сообщения
            if (chat.messages.isEmpty() == false) {
                //получаем только сообщения до определенного количества
                val messages = chat.messages.asSequence()
                //делаем сообщения прочитанными
                messages.filter { it.view == false }.forEach { it.view = true }
                return messages.toList()
            }
            return null
        }
        return null
    }

    //получение списка последних сообщений в чатах
    //используются lambda выражения
    fun printLastMessages(){
        chats.onEach {
            print("ЧАТ С "+it.name+": ")
            if(it.messages.isEmpty()) println(" нет сообщений")
            else println(it.messages)
        }
    }

    //получение списка чатов
    //используются lambda выражения
    fun printChats(){
        val names = getAllNameChats()
        println(names)
    }

    //получение списка чатов, используем Sequence
    fun getAllNameChats(): List<String>{
        val result = sequence{
            for (i in 1..chats.count() - 1){
                yield(chats[i].name)
            }
        }
        return result.toList()
    }

    //получение количества чатов непрочитанных
    //используются lambda выражения, используем Sequence
    fun getUnreadChatsCount(): Int{
        var result = 0
        chats.onEach {if(it.messages.asSequence().count{it.view == false}>0)result++}
        return result
    }

    //вывод, используется обобщение
    //используются lambda выражения
    fun <T> print(list: MutableList<T>) {
        chats.onEach(){
            print(it)
            println()
        }
        println()
    }

    //сброс
    fun reset(): Boolean{
        chats = mutableListOf<Chat>()
        lastIdMessage = 0
        lastIdUser = 0
        lastIdChat = 0
        return true
    }
}