library(neuralnet)

# READ FILE
fadigaData <- read.csv("/Users/fdr/Dropbox/UM 14-15/SRCR/trabalho3/exercicio3.csv")

# ------------------------------------------------------------------------------------
# 300 CASOS PARA TARAINSET
trainset <- fadigaData[1:300, ]

# O RESTANTE PARA TESTSET
testset <- fadigaData[301:844, ]
# ------------------------------------------------------------------------------------

# CRIAR SUBSET PARA TESTE
subset_test <- subset(testset, select = c("Performance.KDTMean","Performance.MAMean",
                                        "Performance.MVMean","Performance.DMSMean",
                                        "Performance.DDCMean","Performance.ADMSLMean",
                                        "Performance.AEDMean","Performance.TBCMean"))  

# ------------------------------------------------------------------------------------
#                 PARA PARA DETERMINAR O NIVEL DE FADIGA
# ------------------------------------------------------------------------------------

# CONSTRUCAO DA REDE
fadigaMental <- neuralnet(FatigueLevel~Performance.KDTMean + Performance.MAMean +
                            Performance.MVMean + Performance.DMSMean + 
                            Performance.ADMSLMean + Performance.AEDMean + 
                            Performance.TBCMean + Performance.DDCMean, 
                            trainset, hidden=c(8,10,16), threshold = 0.05)

# IMPRIME RESULTADOS
print(fadigaMental)

# DESENHAR REDE NEURONAL
plot(fadigaMental)
gwplot(fadigaMental)

# TESTAR O TESTSET NA REDE
fadigaMental.results <-compute(fadigaMental, subset_test)

# RESULTADOS PARA O TESTSET
results <- data.frame(FatigueLevel= testset$FatigueLevel, 
                      NetOutput = round(fadigaMental.results$net.result))

# ACRESCENTAR UMA COLUNA (Cansado) AO RESULTADO OBTIDO APENAS COM A INFORMACAO True/False,
#     SE ESTA CONSADO OU NAO
results["Fadiga"] <- results$Fadiga <- ifelse(results$NetOutput>3,"True","False")

# IMPRIME RESULTADO OBTIDO
print(results)
# ----------------------------------------------------------------------
#             DETERMINAR A FADIGA TENDO EM CONTA A TAREFA
# ----------------------------------------------------------------------
# -------------------
# Work        -> -1  |
# programming ->  0  |
# office      ->  1  |
# -------------------

# CRIAR SUBSET PARA TESTE
subset_test_2 <- subset(testset, select = c("Performance.KDTMean","Performance.MAMean",
                                          "Performance.MVMean","Performance.DMSMean",
                                          "Performance.DDCMean","Performance.ADMSLMean",
                                          "Performance.AEDMean","Performance.TBCMean",
                                          "Performance.Task"))
# CONSTRUCAO DA REDE
fadigaMental_2 <- neuralnet(FatigueLevel ~ Performance.KDTMean + Performance.MAMean +
                            Performance.MVMean + Performance.DMSMean + 
                            Performance.ADMSLMean + Performance.AEDMean + 
                            Performance.TBCMean + Performance.DDCMean +
                            Performance.Task, trainset, hidden=c(8,10,16), 
                            threshold = 0.05)

# IMPRIME RESULTADOS
print(fadigaMental_2)

# DESENHAR REDE NEURONAL
plot(fadigaMental_2)
gwplot(fadigaMental_2)

# TESTAR O TESTSET NA REDE
fadigaMental_2.results <-compute(fadigaMental_2, subset_test_2)

# RESULTADOS PARA O TESTSET
results_2 <- data.frame(FatigueLevel = testset$FatigueLevel, 
                      NetOutput = round(fadigaMental_2.results$net.result))

# ACRESCENTAR UMA COLUNA (Cansado) AO RESULTADO OBTIDO APENAS COM A INFORMACAO True/False,
#     SE ESTA CONSADO OU NAO
results_2["Fadiga"] <- results$Fadiga <- ifelse(results$NetOutput>3,"True","False")

# IMPRIME RESULTADO OBTIDO
print(results_2)




# ----------------------------------------------------------------------
#                 PARA PARA DETERMINAR O TIPO DE TAREFA
# ----------------------------------------------------------------------

# -------------------
# Work        -> -1  |
# programming ->  0  |
# office      ->  1  |
# -------------------

# CONSTRUCAO DA REDE
tasknet <- neuralnet(Performance.Task ~ Performance.KDTMean + Performance.MAMean + 
                       Performance.MVMean  + Performance.TBCMean +
                       Performance.DDCMean + Performance.DMSMean +
                       Performance.AEDMean + Performance.ADMSLMean
                     , trainset, hidden = c(10,16), threshold = 0.01)

# IMPRIMIR OS RESULTADOS
print(tasknet)

# DESENHAR REDE NEURONAL
plot(tasknet)
gwplot(tasknet)

# TESTAR O TESTSET NA REDE
tasknet.results <-compute(tasknet, subset_test)

# RESULTADOS PARA O TESTSET
taskResults <- data.frame(Performance.Task = testset$Performance.Task, 
                          TaskOutput = round(tasknet.results$net.result))

# IMPRIME RESULTADO OBTIDO
print(taskResults)



# ----------------------------------------------------------------------
# ----------------------------------------------------------------------
#   MIDIFICACOES NOS DADOS FORNECIDOS
# ----------------------------------------------------------------------
# ----------------------------------------------------------------------
# Para testar outras variantes do projeto, retiramos algumas das biometricas
# que achamos menus relevantes, para ritirar mais conclusoes

# Biometricas retiradas: Performance.TBCMean;
#                        Performance.DDCMean;
#                        Performance.AEDMean;
# Como as tarefas aqui descritas sao principalmente de interacao atraves de 
# teclado, nao retiramos todas em relacao a movimentacao do rato, 
# mas algumas delas.


# TRANSET MODIFICADO
modif_trainset <- subset(trainset, select = c("Performance.KDTMean","Performance.MAMean",
                                          "Performance.MVMean","Performance.DMSMean",
                                          "Performance.ADMSLMean","FatigueLevel",
                                          "Performance.Task"))

# TESTSET MODIFICADO
modif_testset <- subset(testset, select = c("Performance.KDTMean","Performance.MAMean",
                                             "Performance.MVMean","Performance.DMSMean",
                                             "Performance.ADMSLMean"))

# DETERMINAR O NIVEL DE FADIGA

# CONSTRUCAO DA REDE
modif_fadigaMental <- neuralnet(FatigueLevel ~ Performance.KDTMean + Performance.MAMean +
                            Performance.MVMean + Performance.DMSMean + 
                            Performance.ADMSLMean, modif_trainset, hidden=c(15,10), 
                            threshold = 0.05)

# IMPRIME RESULTADOS
print(modif_fadigaMental)

# DESENHAR REDE NEURONAL
plot(modif_fadigaMental)

# TESTAR O TESTSET NA REDE
modif_fadigaMental.results <-compute(modif_fadigaMental, modif_testset)

# RESULTADOS PARA O TESTSET
modif_results <- data.frame(FatigueLevel = testset$FatigueLevel, 
                      modif_NetOutput = round(modif_fadigaMental.results$net.result))


# ACRESCENTAR UMA COLUNA (Cansado) AO RESULTADO OBTIDO APENAS COM A INFORMACAO True/False,
#     SE ESTA CONSADO OU NAO
modif_results["Fadiga"] <- modif_results$Fadiga <- ifelse(modif_results$modif_NetOutput>3,"True","False")

# IMPRIME RESULTADO OBTIDO
print(modif_results)



# ----------------------------------------------------------------------
# ----------------------------------------------------------------------


