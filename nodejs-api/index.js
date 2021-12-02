const express = require('express')
const moment = require('moment')
const os = require("os")
const app = express()
const port = process.env.PORT || 8080;
let terminating = false;

app.get('/favicon.ico', (req, res) => {
  res.status(204).end()
})

app.get('/*', (req, res) => {
  console.log(`[${moment().format(`YYYY/MM/DD HH:mm:ss`)}] : [terminating: ${terminating}] ${req.url}`);
  res
    .status( terminating ? 500 : 200 )
    .json({
      url: req.url,
      originalUrl: req.originalUrl,
      baseUrl: req.baseUrl,
      os_hostname: os.hostname(),
      query: req.query,
      params: req.params,
      method: req.method,
      upgrade: req.upgrade,
      httpVersion: req.httpVersion,
      headers: req.headers,
  })
})

app.listen(port, () => {
  console.log(`[${moment().format(`YYYY/MM/DD HH:mm:ss`)}] : [terminating: ${terminating}] app listening at http://localhost:${port}`)
})

process.on('SIGTERM', () => {
  terminating = true;
  console.log(`(${new Date()}) : received SIGTERM, exiting gracefully`);
  process.exit(2);
});

// process.on('SIGKILL', () => {
//   console.log(`(${new Date()}) : received SIGKILL, exiting gracefully`);
//   process.exit(99);
// });

process.on('beforeExit', (code) => {
  console.log('Process beforeExit event with code: ', code);
});

process.on('exit', (code) => {
  console.log('Process exit event with code: ', code);
});
