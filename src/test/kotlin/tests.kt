import ru.netology.*
import ru.netology.Service.addChat
import ru.netology.Service.addMessageChat
import ru.netology.Service.chats
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class tests {
    //добавление чатов и сообщений
    fun AddChatsMessages(){
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
    }

    //тест на добавление сообщения в чат
    @Test
    fun Test1(){
        AddChatsMessages()
        val message=Message(0, false, "Тест Тест Тест.", LocalDateTime.of(2024, 10, 12, 7, 55), false)
        assertEquals(true, message.addMessageChat(5))
    }

    //тест на удаление чата
    @Test
    fun Test2(){
        AddChatsMessages()
        assertEquals(true, Service.deleteChat(1))
    }

    //тест на удаление сообщения в чате
    @Test
    fun Test3(){
        AddChatsMessages()
        assertEquals(false,Service.deleteMessageChat(1))
    }

    //тест на получение сообщений в чате до определенного количества
    @Test(expected = PostNotFoundException::class)
    fun Test4(){
        AddChatsMessages()
        Service.getMessagesChat(100, 4) ?: throw PostNotFoundException("ERROR")
    }

    @Test
    fun Test5(){
        AddChatsMessages()
        assertEquals(true,Service.reset())
    }
}