from reportPreprocess import reportProcess
from getIdentifier import getIdentifier
from main import main

reportProcess(r'.\Data\raw_report.txt',r'.\Data\report.txt' )
reportProcess(r'.\Data\raw_component.txt',r'.\Data\component.txt' )

getIdentifier(r'.\Data\raw_report.txt', r'.\Data\identifier.txt')

main(r'.\Data\report.txt', r'.\Corpus\CodeCorpus.txt', r'.\Data\CodeResult.txt', 1.5, 100, 0.3)
main(r'.\Data\component.txt', r'.\Corpus\ComponentCorpus.txt', r'.\Data\ComponentResult.txt', 1.2, 100, 0.5)
main(r'.\Data\identifier.txt', r'.\Corpus\IdentifierCorpus.txt', r'.\Data\IdentifierResult.txt', 1.4, 200, 0.3)