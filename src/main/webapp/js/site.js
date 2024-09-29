document.addEventListener('submit',(e)=>{
    const form=e.target;
    if (form.id==='signup-form'){
        e.preventDefault();
        let name = form.querySelector('[name="userName"]').value;
        let email = form.querySelector('[name="userEmail"]').value;
        let password = form.querySelector('[name="userPassword"]').value;
        let query=`name=${encodeURIComponent(name)}&email=${encodeURIComponent(email)}&password=${encodeURIComponent(password)}`;
        console.log(query);

        fetch(form.action /*+ "?" + query*/,{
            method: 'POST',
            headers: {
                'Content-Type':'application/json'
            },
            body: JSON.stringify({
                action: 'registration',
                name, email, password
            })
        }).then(response => {
            console.log("Raw response:", response); // Логируем ответ сервера
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`); // Явно обрабатываем неуспешный ответ
            }
            return response.json();
        }).then(data => {
            console.log("Parsed response:", data); // Логируем распарсенный ответ
            if (!data.status.isOk) {
                displayError(data.status.phrase, 'error-message');
            } else {
                displaySuccess("Регистрация успешна!", 'success-message');
            }
        }).catch(error => {
            console.error("Error during registration:", error);
            displayError("Пользователь с таким e-mail уже зарегистрирован, войдите или попробуйте снова.");
        });
    }
})

function displayError(message, id){
    const errorContainer = document.getElementById(id);
    if(errorContainer){
        errorContainer.innerText=message;
        errorContainer.style.display ='block';
    }
}

function displaySuccess(message, id){
    const successContainer=document.getElementById(id);
    if(successContainer){
        successContainer.innerText=message;
        successContainer.style.display='block';
    }
}

document.getElementById('loginForm').addEventListener('submit', (e)=> {
    const form=e.target;
    e.preventDefault();
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    fetch(form.action, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, password })
    })
        .then(response => response.json())
        .then(data => {
            if (data.status.isOk) {

                displaySuccess("Авторизация успешна!", 'success-message1');
                // Добавляем задержку в 2 секунды (2000 мс)
                setTimeout(() => {
                    // Закрываем модальное окно
                    const modalElement = document.getElementById('loginModal');
                    const modal = bootstrap.Modal.getInstance(modalElement); // Получаем экземпляр модального окна
                    modal.hide(); // Закрываем модальное окно

                    // Показать кнопку "Out" и скрыть "Sign In"
                    document.getElementById('sign-in-button').style.display = 'none';
                    document.getElementById('sign-out-button').style.display = 'block';
                }, 1000); // 2000 мс = 2 секунды

                // alert('Успешная авторизация.');
                // Успешная аутентификация
                //window.location.reload(); // Перезагрузить страницу или перенаправить
            } else {
                // Ошибка аутентификации
                //alert('Invalid email or password.'); // Или другой метод отображения ошибки
                displayError("Введен неправильный e-mail или пароль", 'error-message1');
            }
        })
        .catch(error => console.error('Error during authentication:', error));
});


window.addEventListener('click', (e) => {
    const modalElement = document.getElementById('updateModal');
    if (e.target === modalElement) {
        modalElement.style.display = 'none'; // Скрываем модальное окно
    }
});

document.getElementById('updateModal').addEventListener('show.bs.modal', (event) => {
    const button = event.relatedTarget; // Кнопка, которая открывает модальное окно
    const userId = button.getAttribute('data-id'); // Получаем ID пользователя
    const userName = button.getAttribute('data-name'); // Получаем имя пользователя
    const userEmail = button.getAttribute('data-email'); // Получаем email пользователя
    const userPassword = button.getAttribute('data-password'); // Получаем пароль пользователя

    // Заполняем поля модального окна
    document.getElementById('userId').value = userId;
    document.getElementById('userName').value = userName;
    document.getElementById('userEmail').value = userEmail;
    document.getElementById('userPassword').value = userPassword; // Убедитесь, что ID и имя соответствуют
});

document.getElementById('updateUser').addEventListener('submit', (e) => {
    e.preventDefault(); // Предотвращаем отправку формы по умолчанию

    const userId = document.getElementById('userId').value;
    const name = document.getElementById('userName').value;
    const email = document.getElementById('userEmail').value;

    fetch('/path/to/update/user', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ userId, name, email }),
    })
        .then(response => response.json())
        .then(data => {
            if (data.status.isOk) {
                // Успешное обновление
                alert('Пользователь успешно обновлён');
                // Закрываем модальное окно
                const modalElement = document.getElementById('updateModal');
                modalElement.style.display = 'none';
                // Обновить список пользователей или выполнить другие действия
            } else {
                // Обработка ошибки
                alert('Ошибка обновления: ' + data.status.phrase);
            }
        })
        .catch(error => {
            console.error('Ошибка при обновлении пользователя:', error);
        });
});