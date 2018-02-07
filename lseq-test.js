// Run `npm install` first to install dependencies
const LSeqTree = require('lseqtree')

function runTest() {
  let s1 = new LSeqTree(1)
  let s2 = new LSeqTree(2)

  s2.applyInsert(s1.insert('<', 0))
  s2.applyInsert(s1.insert('>', 1))

  let ins1 = [], ins2 = []
  for (let i = 0; i < 10; i++) ins1.push(s1.insert('-', i + 1))
  for (let i = 0; i < 10; i++) ins2.push(s2.insert('#', i + 1))

  for (let ins of ins1) s2.applyInsert(ins)
  for (let ins of ins2) s1.applyInsert(ins)

  let str = ''
  for (let i = 0; i < s1.length; i++) str += s1.get(i)
  return str
}

for (let attempt = 1; attempt < 10; attempt++) {
  let str = runTest()
  if (!/^<-*#*>$/.test(str) && !/^<#*-*>$/.test(str)) {
    console.log('Found interleaving on the ' + attempt +
                (['', 'st', 'nd', 'rd'][attempt] || 'th') + ' attempt:')
    console.log(str)
    break
  }
}
