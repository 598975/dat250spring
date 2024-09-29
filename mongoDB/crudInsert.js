const { MongoClient } = require('mongodb');
require('dotenv').config();
const { DATABASEURI } = process.env;

const uri = DATABASEURI;

const client = new MongoClient(uri);
const db = client.db("crud");

async function run() {
    try {
        await db.collection('insert').deleteMany({});

        // Insert one document
        /* await db.collection("insert").insertOne({
            item: 'canvas',
            qty: 100,
            tags: ['cotton'],
            size: { h: 28, w: 35.5, uom: 'cm' }
        });

        const cursor = db.collection("insert").find( { item: 'canvas' } );
        console.log(await cursor.toArray()); */

        // Insert multiple documents
        await db.collection('insert').insertMany([
            {
              item: 'journal',
              qty: 25,
              tags: ['blank', 'red'],
              size: { h: 14, w: 21, uom: 'cm' }
            },
            {
              item: 'mat',
              qty: 85,
              tags: ['gray'],
              size: { h: 27.9, w: 35.5, uom: 'cm' }
            },
            {
              item: 'mousepad',
              qty: 25,
              tags: ['gel', 'blue'],
              size: { h: 19, w: 22.85, uom: 'cm' }
            }

        ]);

        const cursor = db.collection('insert').find({});
        console.log(await cursor.toArray());

    } finally {
        await client.close();
    }
}
run().catch(console.dir);