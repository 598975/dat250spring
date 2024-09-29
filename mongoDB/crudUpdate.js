const { MongoClient } = require('mongodb');
require('dotenv').config();
const { DATABASEURI } = process.env;

const uri = DATABASEURI;

const client = new MongoClient(uri);
const db = client.db("crud");

async function run() {
    try {
        await db.collection('update').deleteMany({});
        await db.collection('update').insertMany([
            {
              item: 'canvas',
              qty: 100,
              size: { h: 28, w: 35.5, uom: 'cm' },
              status: 'A'
            },
            {
              item: 'journal',
              qty: 25,
              size: { h: 14, w: 21, uom: 'cm' },
              status: 'A'
            },
            {
              item: 'mat',
              qty: 85,
              size: { h: 27.9, w: 35.5, uom: 'cm' },
              status: 'A'
            },
            {
              item: 'mousepad',
              qty: 25,
              size: { h: 19, w: 22.85, uom: 'cm' },
              status: 'P'
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
            },
            {
              item: 'sketchbook',
              qty: 80,
              size: { h: 14, w: 21, uom: 'cm' },
              status: 'A'
            },
            {
              item: 'sketch pad',
              qty: 95,
              size: { h: 22.85, w: 30.5, uom: 'cm' },
              status: 'A'
            }
        ]);

        // Update one document
        await db.collection('update').updateOne(
            { item: 'paper' },
            {
              $set: { 'size.uom': 'cm', status: 'P' },
              $currentDate: { lastModified: true }
            }
        );
        
        cursor = db.collection('update').find({
            item: 'paper'
        });
        console.log(await cursor.toArray());

        // Update multiple documents
        await db.collection('update').updateMany(
            { qty: { $lt: 50 } },
            {
              $set: { 'size.uom': 'in', status: 'P' },
              $currentDate: { lastModified: true }
            }
        );

        cursor = db.collection('update').find({
            qty: { $lt: 50 }
        });
        console.log(await cursor.toArray());

        // Replace one document
        await db.collection('update').replaceOne(
            { item: 'paper' },
            {
              item: 'paper',
              instock: [
                { warehouse: 'A', qty: 60 },
                { warehouse: 'B', qty: 40 }
              ]
            }
        );

        cursor = db.collection('update').find({
            item: 'paper'
        });
        console.log(await cursor.toArray());
    } finally {
        await client.close();
    }
}

run().catch(console.dir);