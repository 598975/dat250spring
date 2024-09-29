const { MongoClient } = require('mongodb');
require('dotenv').config();
const { DATABASEURI } = process.env;

const uri = DATABASEURI;

const client = new MongoClient(uri);
const db = client.db("crud");

async function run() {
    try {
        await db.collection('query').deleteMany({});
        await db.collection('query').insertMany([
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
            status: 'A'
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
    
    // Select all
    /* const cursor = db.collection('query').find({}); */

    // Specify equality condition
    /* const cursor = db.collection('query').find({ status: 'D' }); */

    // Specify condition using query operators
    /* const cursor = db.collection('query').find({
         status: { $in: ['A', 'D'] } 
    }); */

    // Specify AND condition
    /* const cursor = db.collection('query').find({
        status: 'A',
        qty: { $lt: 30 }
    }); */

    // Specify OR condition
    /* const cursor = db.collection('query').find({
        $or: [ { status: 'A' }, { qty: { $lt: 30 } } ]
    }); */

    // Specify AND as well as OR condition
    const cursor = db.collection('query').find({
        status: 'A',
        $or: [{ qty: { $lt: 30 } }, { item: { $regex: '^p' } }]
    });

    console.log(await cursor.toArray());
} finally {
    await client.close();
}
}

run().catch(console.dir);
