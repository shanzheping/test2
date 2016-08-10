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
          GetRegInfoResult: resXml('29C7433D6F7C9D6EC8BEF046C6F3B671', 'qbY5oiJPPqtSjBzMSjxX5BP7VETbRmExeR52Vh6sLORNRJ4mZ9wGdyN07pt0Xujtot+v+zNq5At7FscR+xa+00lAaG7hTmLltfNvCImitFeFSrUU8vOc291OcpCk6v6IHHEZSrzxJtwgyyfh5Nkzo3HmRo9c8qUPVQKh4jaxJqEydRvMONVnNEmFvEUE6KhcCNuW94BC9SnF2lvk029oB/v/Btj0dXojkqmSwGGuWAqvj4nz0gfmarRaOaQCP3JtVb9WMgmviVVzuO/4et3eVLjKW0vL9qYiJyAPx/UN3e8OXHhQ/YqKkDDKObRY2CfQrxq6k1DTD03XpS0E1JaoSvivql7snIF034WZTBUZYOpQ9BRJnoJmEMnFgG0URK35Cqf074olx8phTeyNcG7qE1XHY/h5ikmr/RyBzne7UbzZIwRoN0ac2kR5/ZFAlFKdGT8wmHUZkVv9nR9ayDCPGUCB5H4ARsmcOK0tpDpUmzX8EfZBxDi9FaVmusCxCKuIf2YbFCG5nzyqnJhegnCxU5Kew/QDJ0cht5cJbu5/RujIEn/bszTckAaorVjX2o4OqsvtbvYk0lO85b0nWkMbEnFyupbT6eJXdxCGi/40wGW9cGTShsuLRQRh57gWjY0zLINK6a+CA9QmesvGi/KgakfWio3aLKreRUEBgbbtVnEGBtsuubfxyGiR0N5N2C6d8trjW5rnTNN1r0fg5kWBscZr+tHKrA5c6UJCUEJr8a3cS8pT+aTDFZm7NaACklgscsoHJwOcElZckOFJuNUQwigA0jbg2huahFmwzuyjKIJo9FKmk3/Ecut0anwCN8cIjUQeMuWrIttkHnDUeAQ142C3S8KAgRLesMth3nJSbElAqurHnFmLuZqyaunp4qFVaGXKCORpsC23Ey0Cx7xkydKzhp0mbrYv9E8yFhnVuFZ1f6nOphOiuISHgQipyS+HofB9OBc9ouVo2axpmP2mx882fkFt0pHHP6SEdvcSppylSVGdhEfYJPwMM3Fu6O3H6AR5xeL8gLUx3wNKQIN1cQGDuIzLLiAQUwU4QDIRE2VHHWFc85sbk7mOWBYRJp0SPqMzbpxzYT/fZFW5qpfpBfWvWpUiFY8TQRa06u7XO79Fr595RUBSwkuCjB+cQ7fxVXiqqwWBcxIbeG8AxUChwT9QQPTijmNceFRfPuuzMh0RtWmQfTs/IcDy+cbVfL2q')
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
