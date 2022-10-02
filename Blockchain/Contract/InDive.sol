// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.4;

import "./token/ERC20/InDiveToken.sol";
import "./token/ERC20/IERC20.sol";
import "./token/ERC721/InDiveNFT.sol";
import "./token/ERC721/IERC721.sol";
import "./utils/Strings.sol";

contract Indive {

    using Strings for *;

    // 토큰 컨트랙트 주소
    address _InDiveTokenAddress;
    // 토큰 컨트랙트
    IERC20 _InDiveTokenContract;

    event DonationHistory(address artist, address donator, uint256 value, string message);

    // donatorList[아티스트 주소] = 후원자 리스트
    mapping(address => DonationInfo[]) public donatorList;

    // ranking[아티스트 주소][사용자 주소] = 후원
    mapping(address => mapping(address => bool)) public isDonated;

    struct DonationInfo{
        address addr;
        uint256 totalValue;
    }

    constructor () {}

    // IVE 토큰 컨트랙트 연결
    function setTokenContract(address contractAddress) public {
        _InDiveTokenAddress = contractAddress;
        _InDiveTokenContract = IERC20(_InDiveTokenAddress);
    }

    // donate : 컨트랙트 호출자가 to 에게 토큰을 송금한다.
    // 사용자는 InDiveToken 컨트랙트에서 approve를 사용하여 InDive 컨트랙트에 출금할 권한을 준다.
    // InDive 컨트랙트는 토큰을 출금 후 to 에게 송금한다.
    function donate(address _to, uint256 _value, string memory _message) public returns (bool) {

        _InDiveTokenContract.transferFrom(msg.sender, address(this), _value);
        _InDiveTokenContract.approve(address(this), _value);
        // 컨트랙트에 저장된 토큰을 to 에게 전송한다.
        _InDiveTokenContract.transferFrom(address(this), _to, _value);

        // 배열에 후원 값을 저장한다.
        // 후원한 적이 있다면 후원 내용을 갱신한다.
        if(isDonated[_to][msg.sender]){
            for(uint i ; i < donatorList[_to].length ; i++){
                if(donatorList[_to][i].addr == msg.sender){
                    donatorList[_to][i].totalValue += _value;
                    break;
                }
            }
        } else {
            donatorList[_to].push(DonationInfo(msg.sender, _value));
            isDonated[_to][msg.sender] = true;
        }

        // 후원 내용을 블록에 기록한다.
        emit DonationHistory(_to, msg.sender, _value, _message);

        return true;
    }

    // function getDonatorList(address artist) public view returns (string[] memory){
    //     uint256 size = donatorList[artist].length;
    //     string[] memory result = new string[](size);

    //     for(uint i = 0 ; i < donatorList[artist].length ; i++){
    //         address addr = donatorList[artist][i].addr;
    //         uint256 totalValue = donatorList[artist][i].totalValue;

    //         string memory strAddr = Strings.toHexString(addr);
    //         string memory strValue = Strings.toString(totalValue);

    //         result[i] = string(abi.encodePacked(strAddr, "|", strValue));
    //     }

    //     return result;
    // }

    function getDonatorList(address artist) public view returns (string memory){
        string memory result = "[";

        for(uint i = 0 ; i < donatorList[artist].length ; i++){
            address addr = donatorList[artist][i].addr;
            uint256 totalValue = donatorList[artist][i].totalValue;

            string memory strAddr = Strings.toHexString(addr);
            string memory strValue = Strings.toString(totalValue);

            result = string(abi.encodePacked(result, "{", "address:\"", strAddr, "\",totalValue:", strValue,"},"));
        }

        result = string(abi.encodePacked(result, "]"));

        return result;
    }
}