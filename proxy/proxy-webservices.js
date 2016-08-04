'use strict';

var http = require('http');
var soap = require('soap');
var parseString = require('xml2js').parseString;

console.log('');
console.log('PEP WebServices Proxy Server');
console.log('');

var resXml = '<![CDATA[<?xml version="1.0" encoding="UTF-8"?>'
  + '<ROOT>'
  + '<RETURN_CODE><![CDATA[0]]]]>><![CDATA[</RETURN_CODE>'
  + '<RETURN_MSG><![CDATA[交易成功]]]]>><![CDATA[</RETURN_MSG>'
  + '<SIGN_TYPE><![CDATA[MD5]]]]>><![CDATA[</SIGN_TYPE>'
  + '<SIGN><![CDATA[sdj1o21l12j01jas]]]]>><![CDATA[</SIGN>'
  + '<RES_ENCRYPTED><![CDATA[ADJASODU912IJ1213312LJ123J1291281829FB1289ASDQQWQWEQW0102s90ad0180DS]]]]>><![CDATA[</RES_ENCRYPTED>'
  + '</ROOT>]]>';

var myService = {
  RegSjWebService: {
    RegSjWebServiceSoap: {
      NetTest: function(args) {
        console.log('xml:');
        console.log(args.xml);
        parseString(args.xml, function(err, result) {
          console.log(result.ROOT.FUN_CODE);
        });
        return {
          NetTestResult: resXml
        };
      }
    }
  }
};

var xml = require('fs').readFileSync('proxy/test.wsdl', 'utf8'),
server = http.createServer(function(request,response) {
  response.end("404: Not Found: " + request.url);
});

server.listen(8080);
soap.listen(server, '/wsdl', myService, xml)
.log = function(type, data) {
  console.log('type: ' + type);
  console.log('data: ' + data);
};
