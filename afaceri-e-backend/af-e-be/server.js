const express = require('express')
const bodyParser = require('body-parser')
const Sequelize = require('sequelize')
const Op = Sequelize.Op;
const mysql = require('mysql2/promise')
const cors=require('cors')
const port=process.env.PORT || 5050

let conn

//Se creaza baza de date in caz ca aceasta nu exista
mysql.createConnection({
    user: 'root',
    password: 'tomaweb'
})
    .then((connection) => {
        conn = connection
        return connection.query('CREATE DATABASE IF NOT EXISTS books')
    })
    .then(() => {
        return conn.end()
    })
    .catch((err) => {
        console.warn(err.stack)
    })

//Se creeaza o instanta sequelize ce foloseste baza de date creata de noi
const sequelize = new Sequelize('books', 'root', 'tomaweb', {
    dialect: 'mysql'
})

const User=sequelize.define('user',{
    //nume, email, passwd, profile photo, telef
    user_id:{
        type:Sequelize.STRING,
        primaryKey:true,
        allowNull:false,
    },
    name:{
        type: Sequelize.STRING,
        allowNull:false,
    },
    email:{
        type: Sequelize.STRING,
        allowNull:false,
    },
    password:{
        type:Sequelize.STRING,
        allowNull:false,
    },
    address:{
        type:Sequelize.STRING,
        allowNull:true,
    },
    createdAt: Sequelize.DATE, 
    updatedAt: Sequelize.DATE, 

},

{
    timestamps:true
})

const Book=sequelize.define('book',{
    
    book_id:{
        type:Sequelize.INTEGER,
        primaryKey:true,
        allowNull:false,
        autoIncrement:true,
    },
    title:{
        type: Sequelize.STRING,
        allowNull:false,
    },
    author:{
      type: Sequelize.STRING,
      allowNull:false,
  },
    details:{
        type: Sequelize.STRING,
        allowNull:false,
    },
    
    price:{
        type:Sequelize.DOUBLE,
        allowNull:false,
    },
    photo:{
      type:Sequelize.STRING,
      allowNull:false,
  },

    createdAt: Sequelize.DATE, 
    updatedAt: Sequelize.DATE, 

}, 

{
    timestamps:true
})

const Task=sequelize.define('task',{
    //nume, email, passwd, profile photo, telef
    task_id:{
        type:Sequelize.INTEGER,
        allowNull:false,
        autoIncrement:true,
        primaryKey:true,
    },
    version:{
        type:Sequelize.INTEGER,
        allowNull:false,
    },
    project_id:{
        type:Sequelize.INTEGER,
        foreignKey:true,
        allowNull:false,
    },
    title:{
        type: Sequelize.STRING,
        allowNull:false,
    },
    details:{
        type: Sequelize.STRING,
        allowNull:false,
    },
    created_by:{
        type:Sequelize.STRING,
        allowNull:false,
    },
    assigned_to:{
        type:Sequelize.STRING,
        allowNull:true,
    },
    state:{
        type:Sequelize.ENUM,
        allowNull:false,
        values:['new','in_progress','completed','unsuccessful']
    },
    starts_on:{
        type:Sequelize.DATE,
        allowNull:false,
    },
    due_date:{
        type:Sequelize.DATE,
        allowNull:false,
    },
    user_id:{
        type:Sequelize.STRING,
        foreignKey:true,
        allowNull:false,
    },
    createdAt: Sequelize.DATE, 
    updatedAt: Sequelize.DATE, 

},

{
    timestamps:true
})




const BooksUser=sequelize.define('books_users',{
  bu_id:{
        type:Sequelize.INTEGER,
        allowNull:false,
        autoIncrement:true,
        primaryKey:true,
    },
    book_id:{
        type:Sequelize.INTEGER,
        allowNull:false,
        foreignKey:true,
    },
    user_id:{
        type:Sequelize.STRING,
        allowNull:false,
        foreignKey:true,
    },
    createdAt: Sequelize.DATE, 
    updatedAt: Sequelize.DATE, 

},

{
    timestamps:true
})

const app = express()
app.use(cors())
app.use(bodyParser.json())

app.get('/create',async (req, res, next) => {
    try {
        await sequelize.sync({ alter: true })
        res.status(201).json({ message: 'created' })
    } catch (err) {
        next(err)
    }
})


//user
//insert, update, delete,select all, select by pk

//insert project, update project, delete project, select



  //get all books for user
  app.get('/user/:pid/books', async (req, res, next) => {
    try {
        const books = await BooksUser.findAll({
            where:
            {
               user_id: req.params.pid
            }
        })
      if (books) {
        res.status(200).json(books)
      } else {
        res.status(404).json({ message: 'not found' })
      }
    } catch (err) {
        next(err)
    }
})

 //insert into cart
 app.post('/booksuser', async (req, res, next) => {
  try {
      const idul=(await BooksUser.create(req.body)).bu_id
      res.status(201).json(idul)
  } catch (err) {
      next(err)
  }
})


//delete from cart
app.delete('/booksuser/:id',async (req, res, next) => {
  try {
    const buid = await BooksUser.findByPk(req.params.id)
  if (buid) {
    await buid.destroy()
    res.status(202).json({ message: 'accepted' })
  } else {
    res.status(404).json({ message: 'not found' })
  }
  } catch (err) {
      next(err)
  }
})

//delete all for user
app.delete('/booksusers/:id',async (req, res, next) => {
  try {
    const b = await BooksUser.findAll({
        where:
        {
           user_id: req.params.id
        }
    })
  if (b) {
  for(ba of b){
      await ba.destroy()
  }
  res.status(202).json({ message: 'accepted' })
  } else {
  res.status(404).json({ message: 'not found' })
  }
  } catch (err) {
    next(err)
  }
})

//delete from cart
app.delete('/booksusers/:id',async (req, res, next) => {
  try {
    const buid = await BooksUser.findByPk(req.params.id)
  if (buid) {
    await buid.destroy()
    res.status(202).json({ message: 'accepted' })
  } else {
    res.status(404).json({ message: 'not found' })
  }
  } catch (err) {
      next(err)
  }
})
//Users

//insert user

app.post('/user', async (req, res, next) => {
    try {
        await User.create(req.body)
        res.status(201).json({ message: 'created' })
    } catch (err) {
        next(err)
    }
})

//update user
app.put('/user/:id', async (req, res, next) => {
    try {
        let userID = req.params.id
        await User.update(req.body, {
            where: {
                user_id: userID,
            }
        })
        res.status(200).json({ message: "updated" })
    } catch (err) {
        next(err)
    }
})

//delete user
app.delete('/user/:id',async (req, res, next) => {
    try {
      const user = await User.findByPk(req.params.id)
    if (user) {
      await user.destroy()
      res.status(202).json({ message: 'accepted' })
    } else {
      res.status(404).json({ message: 'not found' })
    }
    } catch (err) {
        next(err)
    }
})
  //select user by email
  app.get('/usermail/:email', async (req, res, next) => {
    try {

      const users = await User.findAll({
          where:{email:req.params.email}
        });
      if (users) {
        res.status(200).json(users[0])
      } else {
        res.status(404).json({ message: 'not found' })
      }
    } catch (err) {
      next(err)
    }
  })

//select user by id
app.get('/user/:id', async (req, res, next) => {
    try {
      const user = await User.findByPk(req.params.id)
      if (user) {
        res.status(200).json(user)
      } else {
        res.status(404).json({ message: 'not found' })
      }
    } catch (err) {
      next(err)
    }
  })



  //select all
  app.get('/users', async (req, res, next) => {
    try {
        const users = await User.findAll()
        res.status(200).json(users)
      
    } catch (err) {
      next(err)
    }
  })


  

//select user by id
app.get('/user/:id', async (req, res, next) => {
    try {
      const user = await User.findByPk(req.params.id)
      if (user) {
        res.status(200).json(user)
      } else {
        res.status(404).json({ message: 'not found' })
      }
    } catch (err) {
      next(err)
    }
  })



  //----BOOKS///
  //Users

//insert user

app.post('/book', async (req, res, next) => {
  try {
      await Book.create(req.body)
      res.status(201).json({ message: 'created' })
  } catch (err) {
      next(err)
  }
})




//select book by id
app.get('/book/:id', async (req, res, next) => {
  try {
    const book = await Book.findByPk(req.params.id)
    if (book) {
      res.status(200).json(book)
    } else {
      res.status(404).json({ message: 'not found' })
    }
  } catch (err) {
    next(err)
  }
})



//select all
app.get('/books', async (req, res, next) => {
  try {
      const books = await Book.findAll()
      res.status(200).json(books)
    
  } catch (err) {
    next(err)
  }
})




//select user by id
app.get('/user/:id', async (req, res, next) => {
  try {
    const user = await User.findByPk(req.params.id)
    if (user) {
      res.status(200).json(user)
    } else {
      res.status(404).json({ message: 'not found' })
    }
  } catch (err) {
    next(err)
  }
})

  //middle ware care se ocupa de erori din cauza acelui next
app.use((err, req, res, next) => {
    console.warn(err)
    res.status(500).json({ message: 'server error' })
  })
app.listen(port)


