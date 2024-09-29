const { MongoClient } = require('mongodb');
require('dotenv').config();
const { DATABASEURI } = process.env;

const uri = DATABASEURI;

const client = new MongoClient(uri);
const db = client.db("crud");

async function run() {
    try {
        const cursor = db.collection('insert').find({});
        console.log(await cursor.toArray());
    } finally {
        await client.close();
    }
}

run().catch(console.dir);