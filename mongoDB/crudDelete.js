const { MongoClient } = require('mongodb');
require('dotenv').config();
const { DATABASEURI } = process.env;

const uri = DATABASEURI;

const client = new MongoClient(uri);
const db = client.db("crud");

async function run() {
    try {
        await db.collection('delete').deleteMany({});
        await db.collection('delete').insertMany([
            {
              item: 'journal',
              qty: 25,
              size: { h: 14, w: 21, uom: 'cm' },
              status: 'A'
            },
            {
              item: 'notebook',
              qty: 50,
              size: { h: 8.5, w: 11, uom: 'in' },
              status: 'P'
            },
            {
              item: 'paper',
              qty: 100,
              size: { h: 8.5, w: 11, uom: 'in' },
              status: 'D'
            },
            {
              item: 'planner',
              qty: 75,
              size: { h: 22.85, w: 30, uom: 'cm' },
              status: 'D'
            },
            {
              item: 'postcard',
              qty: 45,
              size: { h: 10, w: 15.25, uom: 'cm' },
              status: 'A'
            }
        ]);

        // Delete all
        /* const result = await db.collection('delete').deleteMany({}); */

        // Delete all that match
        /* const result = await db.collection('delete').deleteMany({ status: 'A' }); */

        // Delete only one that matches
        const result = await db.collection('delete').deleteOne({ status: 'D' });
        
        console.log("Removed docs: " + result.deletedCount);
        console.log(await db.collection('delete').find({}).toArray());
    } finally {
        await client.close();
    }
}

run().catch(console.dir);