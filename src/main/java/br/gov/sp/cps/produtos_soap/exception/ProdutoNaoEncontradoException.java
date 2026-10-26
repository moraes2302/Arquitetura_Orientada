package br.gov.sp.cps.produtos_soap.exception;

import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;

@SoapFault(faultCode = FaultCode.CLIENT, faultStringOrReason = "Produto não encontrado")
public class ProdutoNaoEncontradoException extends RuntimeException {
    public ProdutoNaoEncontradoException(int codigo) {
        super("Produto não encontrado: " + codigo);
    }
}
