Запуск приложения:
- запустить скрипт start.sh


Формулировка задачи: 
1. Реализовать базу данных из трех таблиц (Документ, Версия документа,
Регистрационно-контрольная карточка).
Каждый хранимый в базе Документ имеет несколько Версий документов и
одну Карточку, описывающую основные свойства документа.

-Таблица «Документ»
3. Ключ документа
<целочисленный> documentId
4. Название документа
<строковый> documentName
5. Автор документа (логин)
<строковый> author
-Таблица «Версия документа»
1. Ключ версии документа
<целочисленный> documentVersionId
2. Идентификатор документа
<целочисленный> documentId
3. Пользователь создавший версию(логин)
<строковый> versionAuthor
4. Содержание документа
<bytea> content
-Таблица «Сведения о документе»(регистрационная карточка)
1. Ключ регистрационной карточки
<челочисленный> regCardId
2. Ключ документа
<целочисленный> documentId
3. Входящий номер документа
<строковый> documentIntroNumber
4. Исходящий номер документа
<строковый> documentExternNumber
5. Дата поступления
<дата время> dateIntro
6. Дата снятия с учета
<дата время> dateExtern
Приложение должно обеспечивать следующие операции:
1. «Загрузка нового документа». При загрузке открывается форма:
- пользователю предлагается выбрать файл для загрузки (кнопка)
- заполнить следующие поля
1) Название документа
2) Входящий номер документа
3) Дата поступления (заполняется автоматически readonly) -
устанавливается в текущую дату загрузки документа.
При загрузке нового документа автоматически создается его версия с
номером «1».
2. «Загрузка новой версии документа». Пользователь выбирает документ
и нажимает «Загрузить новую версию». Пользователю предлагается
выбрать файл для загрузки (кнопка). После загрузки создается новая запись в
таблице версий с номером версии «+1» от имеющейся версии документа.
3. «Просмотр документа» - возможность скачать файл на форме содержащей
сведения:
- Название документа
- Входящий номер документа
- Дата поступления
4. «Снять документ с учета» Документ остается в базе данных, однако перед
этим пользователю необходимо заполнить поле:
- «Исходящий номер»
При этом поле Дата снятия с учета заполняется автоматически.
Снятые с учета документы помечаются в таблице красным цветом,
загруженные – зеленым.
