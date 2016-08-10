'use strict';

var http = require('http');
var soap = require('soap');
var parseString = require('xml2js').parseString;

console.log('');
console.log('PEP WebServices Proxy Server');
console.log('');

var resXml = function(sign, resEnc) {
  return '<![CDATA[<?xml version="1.0" encoding="UTF-8"?>'
    + '<ROOT>'
    + '<RETURN_CODE><![CDATA[0]]]]>><![CDATA[</RETURN_CODE>'
    + '<RETURN_MSG><![CDATA[交易成功]]]]>><![CDATA[</RETURN_MSG>'
    + '<SIGN_TYPE><![CDATA[MD5]]]]>><![CDATA[</SIGN_TYPE>'
    + '<SIGN><![CDATA[' + sign + ']]]]>><![CDATA[</SIGN>'
    + '<RES_ENCRYPTED><![CDATA[' + resEnc + ']]]]>><![CDATA[</RES_ENCRYPTED>'
    + '</ROOT>]]>';
};

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
          NetTestResult: resXml('29C7433D6F7C9D6EC8BEF046C6F3B671', 'SQWUfujhjmzuuKE/ZebPtaKNRRn2hCiyNVuw4vpTptmijUUZ9oQosjVbsOL6U6bZJXw65UnnP7sAe9EOtXDqFmTEpLAZOo7QwtIOyZyA8iVZ/o8OSYwPSz9r3vo9hP9Foo1FGfaEKLI1W7Di+lOm2aKNRRn2hCiyNVuw4vpTptmiYHoaJvXEhiM8MHcK9aRZ')
        };
      },
      GetRegInfo: function(args) {
        console.log('xml:');
        console.log(args.xml);
        parseString(args.xml, function(err, result) {
          console.log(result.ROOT.FUN_CODE);
        });
        return {
          GetRegInfoResult: resXml('204FAFAB360F30D2DDF6956A39823ED3', '9U5qC4TnA/oF+2+tGlrHYDx6k7alpZa9Eet6QAWpbIRPd9fubfITuWpcWY0Nv0imdyu1jis4gUrlEmhtnqfn+G6Py66Ut5q659vsBh2einQ4of3T+uvCQh6EVLQoATEKdoaiiOQbfWJie8XYFmJ95SWBY81AsUBJwlfKDLETpoJ4LF+UIdi7US7lB+BYx6llPKStw4Kn3p+mBOrI7gvOEMLXom+du/v3ZV8j757pAxauseoKQ71D2PwyH112+gXTejzfxHSOl6F2gAnXtwjYxwWyHxL2u5C6FAA0MU+nuOfhYhpkOIyNhNwr0Rhlz+KPqwedfc5bmrqB4fgnHGprY9AQwBTcHIVQL2qvpiGJpnBkf6rZvgssltPi9Gk5PCqUnSkZK8uVKIoL0zaMy5jdPL3Diov0MVyKEVImuaYWekeXtStHU4ln6OCAhFua7h9orNcUP1h2f1r/EkBKjzg6dbfFVMFXe6PfzDJ8Uw4vJ1Q+BXjst1Vj6K67Ms5F77QhzMYgv3sy5B55V2ZXo7TtGLrvgHB+I2xdPB4rZ9LogL/cu/GXYiiUh3ghhhoimIgOJ5hmwskn6FtgPsJ1rRxe0QGBOJUzFRUG5sbsV4LIf6xWKdemsK32tGIG6T4DB6eTBZsfJvViFQLGpKD4btPo+GGaouXb2J157hDNFr56AQtvouotowTYiz1S0bKNIaHZbjcMJCJ4vZxAjLWFq5IWnp0HwbD2qXxv8FfYNuvKMLRQ9BRJnoJmEMnFgG0URK35Cqf074olx8phTeyNcG7qE96QFcr1q8s31OV2eoNoHVfZIwRoN0ac2kR5/ZFAlFKdGT8wmHUZkVv9nR9ayDCPGUCB5H4ARsmcOK0tpDpUmzX8EfZBxDi9FaVmusCxCKuIf2YbFCG5nzyqnJhegnCxU5Kew/QDJ0cht5cJbu5/RujIEn/bszTckAaorVjX2o4OqsvtbvYk0lO85b0nWkMbEnFyupbT6eJXdxCGi/40wGW9cGTShsuLRQRh57gWjY0zLINK6a+CA9QmesvGi/KgakfWio3aLKreRUEBgbbtVnEGBtsuubfxyGiR0N5N2C6dPulUrUeCPr1NDcU74jBwSQ98a9310fVzegPL8OfgrMlMlY0lqaG/i1dPbBJ2ccr/95GbspsN/e1Yuz8rtSaVLcTkrYX7O9ksS2HtIzsXIV9DUlcB9LaQVRR+oCOrQCoF')
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
