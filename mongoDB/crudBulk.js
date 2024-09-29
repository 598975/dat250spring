const { MongoClient } = require('mongodb');
require('dotenv').config();
const { DATABASEURI } = process.env;

const uri = DATABASEURI;

const client = new MongoClient(uri);
const db = client.db("crud");

async function run() {
    try {
        await db.collection('bulk').deleteMany({});
        await db.collection("bulk").insertMany([
            { _id: 0, type: "pepperoni", size: "small", price: 4 },
            { _id: 1, type: "cheese", size: "medium", price: 7 },
            { _id: 2, type: "vegan", size: "large", price: 8 }
        ])
        try {
            result = await db.collection("bulk").bulkWrite( [
               { insertOne: { document: { _id: 3, type: "beef", size: "medium", price: 6 } } },
               { insertOne: { document: { _id: 4, type: "sausage", size: "large", price: 10 } } },
               { updateOne: {
                  filter: { type: "cheese" },
                  update: { $set: { price: 8 } }
               } },
               { deleteOne: { filter: { type: "pepperoni"} } },
               { replaceOne: {
                  filter: { type: "vegan" },
                  replacement: { type: "tofu", size: "small", price: 4 }
               } }
            ] )
         } catch( error ) {
            print( error )
         }
    } finally {
        console.log(result)
        await client.close();
    }
}

run().catch(console.dir);