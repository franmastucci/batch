#!/usr/bin/env groovy
// Documentation: https://confluence.alm.europe.cloudcenter.corp/display/C3AL/Pipelines+Introduction
@Library(['scf-pipes-shared-library',
        'jenkins-shared-utils' ,
        'scq-pipeline-library']) _

def config = readYaml text: """
---
DOMAIN: 'SCF'
PROJECT_NAME: 'wh-operational-portal'
APP_NAME: 'ms-spb-wh-representatives'
APP_TYPE: 'MavenDockerHelm'
DEPLOYMENT_TYPE: 'EKS'
PACKAGE_FORMAT: 'jar'
CB_MASTER: 'sce-03-pro'
BLUEGREEN: 'OFF'
DEBUG_MODE: '1'
BUILD_PROXY: '1'
LOG_LEVEL: 'INFO'
TECH_VERSION: 17
F_COMPONENTE_TECNOLOGICO: '7'
F_NIVEL_IDIOMA: '34'
F_FORTIFY: '1'
F_PROJECT: 'wh-operational-portal'
DOCKER_IMAGE: 'https://github.alm.europe.cloudcenter.corp/sce-wh-operational-portal/java-17-micro-docker-image.git'
HELM_TEMPLATE: 'https://github.alm.europe.cloudcenter.corp/sce-wh-operational-portal/helm-java'
ARG_RUN_CLASS_APP: 'com.santander.consumer.westernhub.customer.RepresentativesApplication'
HELM_AUTO_ROLLBACK: '1'
HELM_FORCE: '0'
ECR_REGISTRY: '1'
PRODUCT: 'PDCT0050054'
"""
config.keySet().each{
  env."${it}" = config[it]
}

"${pipelineForTechBranch(env)}"(config)
